
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VarinString {
    private ConcurrentHashMap<String, Object> data;
    private boolean debug = false;

    public VarinString(Map<String, Object> map) {
        data = new ConcurrentHashMap<String, Object>();
        data.putAll(map);
    }

    public void enableLog(boolean b) {
        debug = b;
    }

    public void setData(Map<String, Object> map) {
        data.putAll(map);
    }

    public Map<String, Object> getData() {
        if (data != null) {
            Map<String, Object> map = new HashMap<String, Object>(data);
            return map;
        }
        return null;
    }

    public void resetData() {
        data = new ConcurrentHashMap<String, Object>();
    }

    public String resolve(String input) {
        if (debug) {
            System.out.println("Input: " + input);
        }

        if (input == null) {
            return input;
        }

        char[] charArr = input.toCharArray();

        int count = -1;
        boolean in = false;
        int start = -1;
        int end = -1;

        StringBuilder output = new StringBuilder("");

        boolean skip = false;
        for (int i = 0; i < charArr.length; i++) {
            if (skip) {
                skip = false;
                if (output.charAt(output.length() - 1) == ' ') {
                    output.deleteCharAt(output.length() - 1);
                }
            }

            char c = input.charAt(i);
            if (c == '{') {
                in = true;
                start = i;
            } else if (c == '}') {
                if (in) {
                    in = false;
                    end = i;
                    count = count + 1;

                    // Get var-in-string
                    String varinString = input.substring(start + 1, end);
                    if (debug) {
                        System.out.println("Var-in-string: {" + varinString + "}");
                    }

                    // Get variable
                    String[] strArr = varinString.split("\\?:");
                    String variable = strArr[0];

                    // Get resolved value
                    Object value = _resolve(variable);
                    if ((value == null || variable.isEmpty()) && strArr.length > 1) {
                        variable = strArr[1];
                        value = _resolve(variable);
                    }

                    // If value is empty, skip previous space (if present)
                    if (value instanceof String && ((String) value).isEmpty()) {
                        skip = true;
                    }

                    // Could not resolve, keep the string as it is
                    if (value == null) {
                        value = input.substring(start, end + 1);
                    }

                    // Add resolved value to output string
                    output.append(String.valueOf(value));
                } else {
                    output.append(c);
                }
            } else {
                if (!in) {
                    output.append(c);
                }
            }
        }

        if (skip) {
            if (output.charAt(output.length() - 1) == ' ') {
                output.deleteCharAt(output.length() - 1);
            }
        }

        if (in) {
            String s = input.substring(start);
            output.append(s);
        }

        String resolvedText = output.toString();
        if (debug) {
            System.out.println("Resolved text: " + resolvedText);
        }

        return resolvedText;
    }

    private Object _resolve(String variable) {
        if (variable == null || variable.isEmpty()) {
            return variable;
        }

        variable = variable.trim();

        Object value = null;
        if (variable.length() > 1 && variable.startsWith("'") && variable.endsWith("'")) {
            value = variable.substring(1, variable.length() - 1);
        } else if (data != null && !data.isEmpty()) {
            String[] strArr = variable.split("\\.");
            value = new HashMap<String, Object>(data);
            for (int j = 0; j < strArr.length; j++) {
                if (value instanceof Map) {
                    value = ((Map) value).get(strArr[j]);
                } else if (value instanceof List) {
                    String v = strArr[j];
                    try {
                        int index = Integer.valueOf(v);
                        value = ((List) value).get(index);
                    } catch (Throwable t) {
                        if (debug) {
                            t.printStackTrace();
                        }
                    }
                }
            }
        }
        return value;
    }

    public static String resolve(String input, Map<String, Object> map) {
        VarinString varinString = new VarinString(map);
        return varinString.resolve(input);
    }
}
