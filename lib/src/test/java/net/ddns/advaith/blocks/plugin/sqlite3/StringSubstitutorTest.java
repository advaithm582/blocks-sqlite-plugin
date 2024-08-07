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
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the StringSubstitutor for 'intended' behaviors, including the escape
 * sequence.
 *
 * @since 0.1.0
 * @author Advaith Menon
 */
class StringSubstitutorTest {
    @Test
    void testSimpleTemplateNoEscNoFallback() {
        HashMap v = new HashMap<String, String>();
        v.put("fox", "human");
        v.put("Win98", "fedora40");

        // we test simple string
        assertEquals("fedora40: What does the human say?",
                StringSubstitutor.of("${Win98}: What does the ${fox} say?")
                .render(v));
    }

    @Test
    void testInvalidVarEscapeVar() {
        HashMap v = new HashMap<String, String>();
        v.put("Guido Van Rossum", "James Gosling");
        v.put("Python", "Java");
        assertEquals("${Guido Van Rossum} is the creator of ${Python}",
                StringSubstitutor.of(
                    "${Guido Van Rossum} is the creator of `${Python}")
                .render(v));
    }

    @Test
    void testDefaultVar() {
        HashMap v = new HashMap<String, String>();
        v.put("pres", "Teddy Roosevelt");
        v.put("party", "Republican");
        v.put("fav", "most");

        HashMap k = new HashMap<String, String>();
        k.put("pres", "Jimmy Carter");
        k.put("party", "Democrat");
        k.put("fav", "least");
        // apparently, Americans don't like solar panels on the WH roof. Makes
        // sense for not wanting someone to tell/force you to be environmentally
        // friendly - it has to come from within.
        //
        // It is sad that the bipartisan system doesn't allow for common ground
        // on even obvious issues like climate change.

        StringSubstitutor x = StringSubstitutor.of("${pres}, a ${party}, is "
                + "America's ${fav} favorite president. ${fact}");

        assertEquals(
                "Teddy Roosevelt, a Republican, is America's most favorite "
                + "president. ",
                x.render(v));
        assertEquals(
                "Jimmy Carter, a Democrat, is America's least favorite presiden"
                + "t. ",
                x.render(k));
        // also tests dual render - x should not be affected by render state.
    }

    @Test
    public void testVarNames() {
        assertEquals(
                "Mr. Harber said he was delighted to be asked to take part in "
                + "the project as he was local. ${0end} ${}",
                StringSubstitutor.of("Mr. ${n1me} said he was ${e} to be asked "
                    + "to take part in the ${Pr0jEct} as he was local. ${0end} "
                    + "${}").render(Map.of("n1me", "Harber", "e", "delighted",
                            "0end", "error", "", "error", "Pr0jEct",
                            "project")));
    }
}
