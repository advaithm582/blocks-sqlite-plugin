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

import java.sql.SQLException;
import net.ddns.advaith.blocks.plugin.DAOFactory;
import net.ddns.advaith.blocks.plugin.DAOProxy;

final class SqliteDaoFactory implements DAOFactory {
    private String jdbcUrl;

    SqliteDaoFactory() {
        setFilename("tasks.db");
    }

    void setFilename(String name) {
        this.jdbcUrl = "jdbc:sqlite:" + name;
    }

    public DAOProxy build() throws RuntimeException {
        try {
            return new Proxy(jdbcUrl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
