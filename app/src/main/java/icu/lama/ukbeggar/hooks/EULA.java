package icu.lama.ukbeggar.hooks;

public class EULA {
    public static boolean iAgreeAndWillObey() {
        final String[] RULES = new String[] {
                "You MUST obey the following rules to use this software:",
                "",
                "1. You are NOT allowed to make any profit with this software.",
                "2. You acknowledge that this software has no relations to Lowiro. Any keyword related to Lowiro is a coincident.",
                "3. You acknowledge that this software has no relations to Arcaea. Any keyword related to Arcaea is a coincident.",
                "4. You are not allowed to spread a modified version of this software if the following conditions are matched: ",
                "  a) Your modification is related to class EULA.",
                "  b) You are publishing anything else than the source code.",
                "  c) The only exception is the original author of this project, lamadaemon, grants you the permission to do so.",
                "5. You are NOT allowed to publish any tutorials if the following conditions are matched: ",
                "  a) Your tutorial is guiding the reader/viewer to modify the source code.",
                "  b) Your tutorial is guiding the reader/viewer to reverse engineering this application",
                "  c) Your tutorial is guiding the reader/viewer to violate the rules",
                "  d) The only exception is the original author of this project, lamadaemon, grants you the permission to do so.",
                "6. You acknowledge the library 'libnarchook.so' is closed source, and you are not allowed to reverse engineering.",
                "   Specifically but not limited to these following actions are prohibited:",
                "  a) Decompile",
                "  b) Modify",
                "  c) Republish modified version or the original version",
                "7. THE CONSEQUENCES CAUSED BY THE USE OF THIS SOFTWARE SHALL BE BORNE BY THE USER.",
                "  a) THE CONSEQUENCES may include but not limited to:",
                "      i) Your game account get banned.",
                "     ii) Your house get burned.",
                "    iii) The third world war.",
                "     iv) The god decided to punish people and you are the first one.",
                "",
                "",
                "Any violation of the rules will cause the author of this project to suspend updates."
        };

        final String HINT_A = "Make this function return true to represent that you have read the rules and will obey them.";
        final String HINT_B = "If you are using smali editor, make this function return 1 (1 means true)";
        final String HINT_C = "Don't forget to change getTargetPackage return value to beggar game's package name.";
        return false;
    }

    public static String getTargetPackage() {
        String target = "<ReplaceMe>";

        return target;
    }
}
