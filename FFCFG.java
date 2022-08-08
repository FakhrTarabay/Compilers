package csen1002.main.task6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @name Fakhreldin Hussein Soliman
 * @id 43-11215
 * @labNumber 19
 */

public class FFCFG {
    String[] rules;
    LinkedHashMap first;
    ArrayList alpha;

    public FFCFG(String description) {
        this.rules = description.split(";");
        this.first = null;
        this.alpha = new ArrayList();
    }
    public String first() {
        return firstHelper(rules);
    }
    public String firstHelper(String[] rules) {
        LinkedHashMap<String, String> res = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> prev = new LinkedHashMap<String, String>();
        for (int i = 0; i < rules.length; i++) {
            res.put(rules[i].split(",")[0], "");
        }
        boolean flag = true;
        while (flag) {
            for (int i = 0; i < rules.length; i++) {
                boolean e = false;
                String[] rule = rules[i].split(",");
                for (int j = 1; j < rule.length; j++) {
                    char[] section = rule[j].toCharArray();
                    for (int k = 0; k < section.length; k++) {
                        if (Character.isLowerCase(section[k])) {
                            res.put(rule[0], res.get(rule[0]) + doesContain(res.get(rule[0]), section[k] + "", true));
                            e = false;
                            break;
                        } else {
                            if (res.get(section[k] + "").contains("e")) {
                                res.put(rule[0], res.get(rule[0]) + doesContain(res.get(rule[0]), res.get(section[k] + ""), false));
                                e = true;
                            } else {
                                res.put(rule[0], res.get(rule[0]) + doesContain(res.get(rule[0]), res.get(section[k] + ""), false));
                                e = false;
                                break;
                            }
                        }
                    }
                    if (e) {
                        res.put(rule[0], res.get(rule[0]) + doesContain(res.get(rule[0]), "e", true));
                    }
                }
            }
            if (res.equals(prev)) {
                flag = false;
            } else {
                prev = (LinkedHashMap<String, String>) res.clone();
            }
        }
        first = (LinkedHashMap<String, String>) res.clone();
        String fres = "";
        for (HashMap.Entry<String, String> set :
                res.entrySet()) {
            fres += set.getKey() + "," + sortString(set.getValue()) + ";";
        }
        return fres.substring(0, fres.length() - 1);
    }

    public String doesContain(String x, String y, boolean z) {
        String res = "";
        String[] r = y.split("");
        for (int i = 0; i < r.length; i++) {
            if (r[i].equals("e") && !z) {
                continue;
            }
            if (!x.contains(r[i])) {
                res += r[i];
            }
        }
        return res;
    }

    public String sortString(String x) {
        char charArray[] = x.toCharArray();
        Arrays.sort(charArray);
        String res = new String(charArray);
        if (charArray[0] == '$') {
            return res.substring(1) + res.charAt(0);
        }
        return res;
    }

    public ArrayList getIndex(String x, String y) {
        ArrayList res = new ArrayList();
        for (int i = 0; i < y.length(); i++) {
            if (x.equals(y.charAt(i) + "")) {
                res.add(i);
                if (i + 1 == y.length()) {
                    res.add("-");
                }
            }
        }
        return res;
    }

    public String follow() {
        firstHelper(rules);
        LinkedHashMap<String, String> res = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> prev = new LinkedHashMap<String, String>();
        for (int i = 0; i < rules.length; i++) {
            res.put(rules[i].split(",")[0], "");
            alpha.add(rules[i].split(",")[0]);
        }
        res.put("S", "$");
        boolean flag = true;
        while (flag) {
            for (int a = 0; a < alpha.size(); a++) {
                for (int i = 0; i < rules.length; i++) {
                    String[] rule = rules[i].split(",");
                    for (int j = 1; j < rule.length; j++) {
                        if (rule[j].contains((String) alpha.get(a))) {
                            ArrayList indeces = getIndex((String) alpha.get(a), rule[j]);
                            int t1 = indeces.size();
                            for (int l = 0; l < t1; l++) {
                                boolean eflag = false;
                                char[] section = null;
                                if (indeces.size() == 1) {
                                    section = rule[j].substring((int) indeces.get(0) + 1).toCharArray();
                                } else {
                                    if (indeces.get(1).equals("-")) {
                                        res.put((String) alpha.get(a), res.get(alpha.get(a)) + doesContain(res.get(alpha.get(a)), res.get(rule[0]), false));
                                        break;
                                    } else {
                                        if (indeces.size() != 1) {
                                            if ((int) indeces.get(0) + 1 == (int) indeces.get(1)) {
                                                section = rule[j].substring((int) indeces.get(0) + 1).toCharArray();
                                                indeces.remove(0);
                                            } else {
                                                section = rule[j].substring((int) indeces.get(0) + 1, (int) indeces.get(1)).toCharArray();
                                                indeces.remove(0);
                                            }
                                        }
                                    }
                                }
                                for (int k = 0; k < section.length; k++) {
                                    if (Character.isLowerCase(section[k])) {
                                        res.put((String) alpha.get(a), res.get(alpha.get(a)) + doesContain(res.get(alpha.get(a)), section[k] + "", false));
                                        eflag = false;
                                        break;
                                    } else {
                                        String t = ((String) first.get(section[k] + ""));
                                        res.put((String) alpha.get(a), res.get(alpha.get(a)) + doesContain(res.get(alpha.get(a)), t, false));
                                        if (t.contains("e")) {
                                            eflag = true;
                                        } else {
                                            eflag = false;
                                            break;
                                        }
                                    }
                                }
                                if (eflag) {
                                    res.put((String) alpha.get(a), res.get(alpha.get(a)) + doesContain(res.get(alpha.get(a)), res.get(rule[0]), false));
                                }
                            }
                        }
                    }
                }
            }
            if (res.equals(prev)) {
                flag = false;
            } else {
                prev = (LinkedHashMap<String, String>) res.clone();
            }
        }
        String fres = "";
        for (HashMap.Entry<String, String> set :
                res.entrySet()) {
            fres += set.getKey() + "," + sortString(set.getValue()) + ";";
        }
        return fres.substring(0, fres.length() - 1);
    }
}