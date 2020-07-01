package br.com.eleicao.caboeleitorais.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.eleicao.caboeleitorais.model.Setor

@Dao
interface SetorDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salvaTodos(setores: List<Setor>)

    @Query("SELECT * FROM Setor")
    fun buscarTodos(): LiveData<List<Setor>>

    @Query("SELECT * FROM Setor WHERE nome = :nome")
    fun buscarPorNome(nome: String): Setor?

}