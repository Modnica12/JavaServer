package com.faceless.sql.migration;

import java.sql.SQLException;

public abstract class Migration
{
    public abstract void up() throws SQLException; // Change DB for new program version

    public abstract void down() throws SQLException; // Cancel changes
}
