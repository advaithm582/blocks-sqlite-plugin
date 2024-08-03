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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.ddns.advaith.blocks.plugin.DAOProxy;
import net.ddns.advaith.blocks.model.GenericDAO;
import net.ddns.advaith.blocks.model.Task;

class Proxy implements DAOProxy {
    private Connection con;

    Proxy(String jdbcUrl) throws SQLException {
        con = DriverManager.getConnection(jdbcUrl);
        autoMigrate();
    }

    // TODO: automatic setup and migration
    private void autoMigrate() {
        return;
    }

    public GenericDAO<Task> getTaskDAO() {
        throw new UnsupportedOperationException("not implemented");
    }
}
