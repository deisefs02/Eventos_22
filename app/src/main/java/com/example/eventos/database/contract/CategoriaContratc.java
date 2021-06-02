package com.example.eventos.database.contract;

import com.example.eventos.database.Entity.CategoriaEntity;

public final class CategoriaContratc {

    private CategoriaContratc(){}

    public static final String criarTabela(){
       return "CRIATE TABLE " + CategoriaEntity.TABLE_NAME + " (" +
       CategoriaEntity._ID + " INTEGER PRIMARY KEY, " +
       CategoriaEntity.COLUNN_NAME + " TEXT)";
    }

    public static final String removerTabela(){
        return "DROP TABLE IF EXISTS " + CategoriaEntity.TABLE_NAME;
    }
}
