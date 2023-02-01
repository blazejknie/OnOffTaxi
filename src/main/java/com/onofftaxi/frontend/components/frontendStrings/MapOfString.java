package com.onofftaxi.frontend.components.frontendStrings;

import java.util.HashMap;
import java.util.Map;

public class MapOfString {

    private Map<String, String> map;

    public MapOfString() {
        map = new HashMap<>();
        appInfo();
        appInfoLabel();
        buttonOk();
    }

    public String get(String key) {
        return map.get(key);
    }

    private void appInfo() {
        String appinfo = "Serwis onofftaxi.pl używa informacji zapisanych za pomoocą plików cookies i podobnych technologii w celach zachowania prawidłowego działania, utrzymania bezpieczeństwa oraz do celów reklamowych i statystycznych serwisu.\n" +
                "Korzystając z serwisu onofftaxi.pl wyrażasz zgodę na używanie plików cookies, zgodnie z aktualnymi ustawieniami przeglądarki.\n" +
                "W każdej chwili możesz dokonać zmiany ustawień dotyczących plików cookies w swojej przeglądarce internetowej.\n";

        map.put("appInfoDialog", appinfo);
    }

    private void appInfoLabel() {
        String appinfoLabel = "Z tych powodów do poprawnego działania aplikacji potrzebna jest zgoda na udostępnienie lokalizacji i używania plików cookie.";

        map.put("appInfoLabel", appinfoLabel);
    }

    private void buttonOk(){
        String buttonOk = "Rozumiem i akceptuję.";
        map.put("buttonOk", buttonOk);
    }


}
