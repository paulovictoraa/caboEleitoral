package br.com.eleicao.caboeleitorais.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class Mask {

    public enum MaskType {
        CNPJ("##.###.###/####-##"),
        CPF("###.###.###-##"),
        CEP("#####-###"),
        TEL("(##) # ########"),
        DATA_NASC("##/##/####");

        String mask;

        MaskType(String s) {
            mask = s;
        }

        public String getMask() {
            return mask;
        }
    }


    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[ ]", "").replaceAll("[)]", "");
    }

    public static String mask(MaskType type, String s) {
        StringBuilder result = new StringBuilder(s);

        if (!s.contains(".")) {
            String str = Mask.unmask(s);
            result = new StringBuilder();

            int i = 0;
            for (char m : type.getMask().toCharArray()) {
                if (m != '#') {
                    result.append(m);
                    continue;
                }
                try {
                    result.append(str.charAt(i));
                } catch (Exception e) {
                    break;
                }
                i++;
            }
        }

        return result.toString();
    }


    public static TextWatcher insert(final MaskType type, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (isUpdating) {
                    isUpdating = false;
                    old = s.toString();
                    return;
                }

                if (!s.toString().isEmpty() && (s.toString().length() > old.length())) {
                    String str = Mask.unmask(s.toString());
                    String mask = "";

                    int i = 0;
                    for (char m : type.getMask().toCharArray()) {
                        if (m != '#') {
                            mask += m;
                            continue;
                        }
                        try {
                            mask += str.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                    isUpdating = true;
                    ediTxt.setText(mask);
                    ediTxt.setSelection(mask.length());
                } else {
                    old = s.toString();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    public static String formatarCnpjCpf(String cnpjCpf) {
        if (cnpjCpf == null) return "";
        if (cnpjCpf.length() == 11) {
            return Mask.mask(Mask.MaskType.CPF, cnpjCpf);
        } else if (cnpjCpf.length() == 14) {
            return Mask.mask(Mask.MaskType.CNPJ, cnpjCpf);
        } else {
            return "";
        }
    }

}