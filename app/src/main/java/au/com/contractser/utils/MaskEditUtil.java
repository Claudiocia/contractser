package au.com.contractser.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskEditUtil {
    public static final String FORMAT_PHONE = "(###)#-###-###-###";
    public static final String FORMAT_POST_CODE = "####";
    public static final String FORMAT_DATE = "##/##/####";
    public static final String FORMAT_PHONE_WA = "(###)####################";

    public static TextWatcher mask(final EditText editTxt, final String mask){
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String str = MaskEditUtil.unmask(s.toString());
                String mascara = "";
                if (isUpdating){
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (final char m : mask.toCharArray()){
                    if (m != '#' && str.length() > old.length()){
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    }catch (final Exception e){
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editTxt.setText(mascara);
                editTxt.setSelection(mascara.length());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public static String unmask(final String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[ ]","").replaceAll("[:]", "").replaceAll("[)]", "");
    }
}
