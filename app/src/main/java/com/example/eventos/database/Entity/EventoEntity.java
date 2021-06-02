package com.example.eventos.database.Entity;

import android.provider.BaseColumns;

public final class EventoEntity implements BaseColumns {

    private EventoEntity(){}

    public static final String TABLE_NAME = "evento";
    public static final String COLUMN_NAME = "nome";
    public static final String COLUMN_LOCAL = "local";
    public static final String COLUMN_DATA = "data";

}
