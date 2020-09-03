package br.com.eleicao.caboeleitorais.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import br.com.eleicao.caboeleitorais.model.eleitor.EleitorPersistence

@Dao
interface EleitorDAO {

    @Query("SELECT * FROM Eleitor ORDER BY NOME_CONSULTA")
    fun buscaTodos(): DataSource.Factory<Int, EleitorPersistence>

    @Query("SELECT * FROM Eleitor WHERE NOME_CONSULTA LIKE :nome AND SETOR = :setor AND CABO_ELEITORAL = :cabo ORDER BY NOME_CONSULTA")
    fun buscaPorNomeESetorECabo(
        nome: String,
        setor: String,
        cabo: String
    ): DataSource.Factory<Int, EleitorPersistence>

    @Query("SELECT * FROM Eleitor WHERE NOME_CONSULTA LIKE :nome AND SETOR = :setor ORDER BY NOME_CONSULTA")
    fun buscaPorNomeESetor(
        nome: String,
        setor: String
    ): DataSource.Factory<Int, EleitorPersistence>

    @Query("SELECT * FROM Eleitor WHERE NOME_CONSULTA LIKE :nome AND CABO_ELEITORAL = :cabo ORDER BY NOME_CONSULTA")
    fun buscaPorNomeECabo(
        nome: String,
        cabo: String
    ): DataSource.Factory<Int, EleitorPersistence>

    @Query("SELECT * FROM Eleitor WHERE SETOR = :setor AND CABO_ELEITORAL = :cabo ORDER BY NOME_CONSULTA")
    fun buscaPorSetorECabo(
        setor: String,
        cabo: String
    ): DataSource.Factory<Int, EleitorPersistence>

    @Query("SELECT * FROM Eleitor WHERE SETOR = :setor ORDER BY NOME_CONSULTA")
    fun buscaPorSetor(setor: String): DataSource.Factory<Int, EleitorPersistence>

    @Query("SELECT * FROM Eleitor WHERE CABO_ELEITORAL = :cabo ORDER BY NOME_CONSULTA")
    fun buscaPorCabo(cabo: String): DataSource.Factory<Int, EleitorPersistence>

    @Query("SELECT * FROM Eleitor WHERE NOME_CONSULTA LIKE :nome ORDER BY NOME_CONSULTA")
    fun buscaPorNome(nome: String): DataSource.Factory<Int, EleitorPersistence>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(eleitor: EleitorPersistence): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salvaTodos(eleitor: List<EleitorPersistence>)

    @Delete
    fun delete(eleitor: EleitorPersistence)

    @Query("SELECT * FROM Eleitor WHERE codigo = :id")
    fun buscaPorId(id: Long): LiveData<EleitorPersistence>

    @Query("SELECT * FROM Eleitor  WHERE data_insercao = ''")
    fun buscaTodosNaoEnviados(): List<EleitorPersistence>

    @Query("DELETE FROM ELEITOR")
    fun deleteAll()

}
