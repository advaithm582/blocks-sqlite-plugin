/*
 * This file is part of Blocks.
 * 
 * Blocks is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Blocks is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with Blocks. If not, see <https://www.gnu.org/licenses/>. 
 */

package net.ddns.advaith.blocks.plugin.sqlite3;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple template-based substitutions. Anything contained in
 * <span style="font-family:monospace;">${[a-zA-Z][a-zA-Z0-9._-]}</span> will be
 * replaced with a value from the Map provided to the parse stage.
 *
 * @since 0.1.0
 * @author Advaith Menon
 */
class StringSubstitutor {
    // Logger object
    private static final Logger LOGGER =
        LoggerFactory.getLogger(StringSubstitutor.class);

    // the main regex needed for it all
    private static final Pattern VAR_SUB = Pattern.compile(
            "(?<!`)\\$\\{([a-zA-Z][a-zA-Z0-9._-]*)\\}");

    private static final Pattern ESC_SUB = Pattern.compile(
            "`\\$\\{([a-zA-Z][a-zA-Z0-9._-]*)\\}");

    private Matcher matcherVarSub;
    private Matcher matcherEscSub;
    private String input;

    protected StringSubstitutor(String input) {
        matcherVarSub = VAR_SUB.matcher(input);
        matcherEscSub = ESC_SUB.matcher(input);
        this.input = input;
    }

    /**
     * Create a new StringSubstitutor renderer out of a String.
     *
     * @param input The input string to read from
     * @return A new StringSubstitutor environment
     */
    public static final StringSubstitutor of(String input) {
        return new StringSubstitutor(input);
    }

    /*
     * Render a ${} level. Assumes matcher is reset.
     */
    private void renderDollarBraces(StringBuilder sb,
            Map<String, String> vars) {
        while (matcherVarSub.find()) {
            String x = matcherVarSub.group(1);
            String r;
            if (vars.containsKey(x)) {
                r = vars.get(x);
            } else {
                LOGGER.warn("Variable not found: {}, replaceing with empty "
                        + "string");
                r = "";
            }
            matcherVarSub.appendReplacement(sb, r);
        }
        matcherVarSub.appendTail(sb);
    }

    /*
     * Replace `${} with ${}.
     */
    private void unescapeBackDollar(StringBuilder sb) {
        while (matcherEscSub.find()) {
            matcherEscSub.appendReplacement(sb, "\\${$1}");
        }
        matcherEscSub.appendTail(sb);
    }

    /**
     * Render the given template with the variables in the Map. If the
     * variable does not exist in the Map, it is substituted with a blank
     * string and a log message is emitted.
     *
     * @param vars The String to String key-value map.
     * @return The rendered template.
     */
    public String render(Map<String, String> vars) {
        matcherVarSub.reset(); // allows multiple calls
        StringBuilder sb = new StringBuilder();
        renderDollarBraces(sb, vars);
        matcherEscSub.reset(sb.toString());
        sb.setLength(0);
        unescapeBackDollar(sb);
        return sb.toString();
    }
}

