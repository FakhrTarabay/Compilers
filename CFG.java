package csen1002.main.task5;

import java.util.*;

/**
 * @name Fakhreldin Hussein Soliman
 * @id 43-11215
 * @labNumber 19
 */
public class CFG {
    String[] vars;

    public CFG(String description) {
        this.vars = description.split(";");
    }

    public String lre() {
        ArrayList alpha = new ArrayList();
        HashMap<String, String> x = new HashMap<String, String>();
        String res;
        String prev;
        String[] rule;
        boolean flag;
        boolean flag2;
        for (int ii = 0; ii < vars.length; ii++) { //loop over rules
            flag = true;
            x.put(vars[ii].substring(0, 1), vars[ii].substring(2));
            alpha.add(vars[ii].substring(0, 1));
            res = "";
            prev = "";
            while (flag) {
                rule = x.get(alpha.get(ii)).split(",");
                for (int xx = 0; xx < rule.length; xx++) { //loop over rule sections
                    flag2 = true;
                    for (int jj = 0; jj <= ii; jj++) { //loop over alphabet
                        if (rule[xx].substring(0, 1).equals(alpha.get(jj)) && !alpha.get(ii).equals(alpha.get(jj))) {
                            res += "," + StringCrossProd(rule[xx], x.get(alpha.get(jj)), (String) alpha.get(jj));
                            res = cleanString(res);
                            flag2 = false;
                            break;
                        }
                    }
                    if (flag2) {
                        res += "," + rule[xx];
                    }
                }
                if (res.equals(prev)) {
                    flag = false;
                    res = cleanString(res);
                    x.put((String) alpha.get(ii), res);
                } else {
                    prev = res;
                    res = cleanString(res);
                    x.put((String) alpha.get(ii), res);
                    res = "";
                }
            }
            rule = x.get(alpha.get(ii)).split(",");
            for (int xx = 0; xx < rule.length; xx++) {
                if (st(rule, (String) alpha.get(ii))) {
                    res = "";
                    for (int j = 0; j < x.get(alpha.get(ii)).split(",").length; j++) {
                        if (alpha.get(ii).equals(x.get(alpha.get(ii)).split(",")[j].substring(0, 1))) {
                            x.put(alpha.get(ii) + "'", (x.get(alpha.get(ii) + "'") == null ? "" : x.get(alpha.get(ii) + "'") + ",")
                                    + x.get(alpha.get(ii)).split(",")[j].substring(1) + alpha.get(ii) + "'");
                        } else {
                            res += x.get(alpha.get(ii)).split(",")[j] + alpha.get(ii) + "',";
                        }
                    }
                    x.put((String) alpha.get(ii), res.substring(0, res.length() - 1));
                    if (x.get(alpha.get(ii) + "'") != null)
                        x.put(alpha.get(ii) + "'", x.get(alpha.get(ii) + "'") + "," + "e");
                    break;
                }
            }
        }
        res = "";
        for (int f = 0; f < alpha.size(); f++) {
            String r = (String) alpha.get(f);
            res += r + "," + x.get(r) + ";" + (x.get(r + "'") != null ? r + "'," + x.get(r + "'") + ";" : "");
        }
        return res.substring(0, res.length() - 1);
    }

    public String cleanString(String x) {
        String res = "";
        String[] y = x.split(",");
        for (int i = 0; i < y.length; i++) {
            if (!y[i].equals(""))
                res += y[i] + ",";
        }
        return res.substring(0, res.length() - 1);
    }

    public boolean st(String[] rule, String x) {
        for (int i = 0; i < rule.length; i++) {
            if (x.equals(rule[i].substring(0, 1)))
                return true;
        }
        return false;
    }

    public String StringCrossProd(String q, String w, String c) {
        String res = "";
        String[] l = w.split(",");
        for (int b = 0; b < l.length; b++) {
            res += (q.length() > 1) ? (l[b] + q.substring(1) + ",") : (q.equals(c)) ? l[b] + "," : (l[b] + q + ",");
        }
        return res;
    }
}
