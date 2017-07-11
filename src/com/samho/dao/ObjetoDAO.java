/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.dao;

import com.samho.util.Registrador;
import com.samho.util.CamposSubstituirDecricao;
import com.samho.util.Formatacao;
import com.samho.util.GridJTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JTable;

/**
 * Classe ObjetoDAO
 *
 * @author Saibel, Sergio Luis
 * @since date 19/04/2017
 *
 * @version revision 001.20170419 date 19/04/2017 author Saibel, Sergio Luís
 * reason Alteração em procedimentos para adaptação da alteração no objeto
 * DadosDAO.
 *
 * revision 001.20170331 date 31/03/2017 author Saibel, Sergio Luís reason
 * permitir as operações com banco de dados. Esta classe é responsável por
 * inserir, alterar ou excluir um objeto do banco, bem como outras
 * funcionalidades comuns a todos os objetos.
 *
 */
public class ObjetoDAO implements InterfaceDAO {

    // Declaração de atributos
    private String tabela;
    private final ArrayList<DadosDAO> camposTabela;
    private final ArrayList<DadosDAO> condicaoWhere;
    private final ArrayList<CamposSubstituirDecricao> listaCamposDescricao;

    private final String DESCR_CAMPO_DESCR = "";

    // Construtor
    public ObjetoDAO(String tabela) {
        this.tabela = tabela;
        camposTabela = new ArrayList<>();
        condicaoWhere = new ArrayList<>();
        listaCamposDescricao = new ArrayList<>();

        preencherLista();
    }

    @Override
    public Registrador popularCodigoDescricao(String campoID, String descricao) {
        Registrador retorno = new Registrador();

        try {
            Statement st
                    = ConexoesDB.getInstance().getConnection().createStatement();

            String sql = " SELECT " + campoID + ", " + descricao
                    + " FROM " + tabela + addWere() + ";";

            ResultSet rs = st.executeQuery(sql);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    retorno.setCodigo(rs.getLong(1));
                    retorno.setDescricao(rs.getString(2));
                }
            }
        } catch (SQLException SQLe) {
            retorno.setCodigo(-1);
            retorno.setDescricao("Erro ao efetuar pesquisa: " + SQLe);
        }

        return retorno;
    }

    @Override
    public String incluir() {
        String retorno = null;

        try {
            Statement st
                    = ConexoesDB.getInstance().getConnection().createStatement();

            String campos = "";
            String valores = "";
            for (int i = 0; i < camposTabela.size(); i++) {
                DadosDAO dadoDAO = camposTabela.get(i);

                campos += dadoDAO.getCampo();

                if ((dadoDAO.getTipo() == DadosDAO.TIPO_INTEGER)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_DOUBLE)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_BOOLEAN)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_LONG)) {
                    if (dadoDAO.getValor().equals("-1")) {
                        valores += null;
                    } else {
                        valores += dadoDAO.getValor().trim();
                    }
                } else {
                    if ((dadoDAO.getTipo() == DadosDAO.TIPO_STRING)
                            || (dadoDAO.getTipo() == DadosDAO.TIPO_DATE)) {
                        if (dadoDAO.getValor() == null) {
                            valores += dadoDAO.getValor();
                        } else {
                            valores += "'" + dadoDAO.getValor().trim() + "'";
                        }
                    }
                }

                if (i < (camposTabela.size() - 1)) {
                    campos += ",";
                    valores += ",";
                }
            }

            String sql = " INSERT INTO " + tabela + " (" + campos + ")"
                    + " VALUES (" + valores + ");";

            st.executeUpdate(sql);
        } catch (SQLException SQLe) {
            retorno = "Erro salvar " + tabela + ": " + SQLe;
        }

        return retorno;
    }

    @Override
    public String alterar() {
        String retorno = null;

        try {
            Statement st
                    = ConexoesDB.getInstance().getConnection().createStatement();

            String sql = "";
            sql += " UPDATE " + tabela + " SET ";
            for (int i = 0; i < camposTabela.size(); i++) {
                DadosDAO dadoDAO = camposTabela.get(i);

                sql += dadoDAO.getCampo() + "=";

                if ((dadoDAO.getTipo() == DadosDAO.TIPO_INTEGER)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_DOUBLE)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_BOOLEAN)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_LONG)) {
                    if (dadoDAO.getValor().equals("-1")) {
                        sql += null;
                    } else {
                        sql += dadoDAO.getValor().trim();
                    }
                } else {
                    if ((dadoDAO.getTipo() == DadosDAO.TIPO_STRING)
                            || (dadoDAO.getTipo() == DadosDAO.TIPO_DATE)) {
                        if (dadoDAO.getValor() == null) {
                            sql += dadoDAO.getValor();
                        } else {
                            sql += "'" + dadoDAO.getValor().trim() + "'";
                        }
                    }
                }

                if (i < (camposTabela.size() - 1)) {
                    sql += ",";
                }
            }

            sql += addWere() + ";";

            st.executeUpdate(sql);
        } catch (SQLException SQLe) {
            retorno = "Erro alterar " + tabela + ": " + SQLe;
        }

        return retorno;
    }

    @Override

    public String excluir(String id) {
        String retorno = null;

        try {
            Statement st
                    = ConexoesDB.getInstance().getConnection().createStatement();

            String sql
                    = "SELECT delete_cascade('public','" + tabela.toLowerCase()
                    + "','" + id + "');";

            st.executeQuery(sql);
        } catch (SQLException SQLe) {
            retorno = "Erro excluir " + tabela + ": " + SQLe;
        }

        return retorno;
    }

    @Override
    public String popularListaJComboBox(JComboBox combo, String campoCodigo,
            String campoDescricao) {
        String retorno = null;

        Registrador dadoPesquisa = new Registrador();
        dadoPesquisa.setCodigo(0);
        dadoPesquisa.setDescricao("Selecionar...");
        combo.addItem(dadoPesquisa.getDescricao());

        try {
            Statement st
                    = ConexoesDB.getInstance().getConnection().createStatement();

            String sql = " SELECT " + campoCodigo + ", " + campoDescricao
                    + " FROM " + tabela;

            ResultSet rs = st.executeQuery(sql);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    dadoPesquisa = new Registrador();

                    dadoPesquisa.setCodigo(rs.getLong(1));
                    dadoPesquisa.setDescricao(rs.getString(2));

                    combo.addItem(dadoPesquisa.getDescricao());
                }
            }
        } catch (SQLException SQLe) {
            retorno = "Erro ao popular combo: " + SQLe;
        }

        return retorno;
    }

    /**
     * Retorna o campo ID da tabela
     *
     * @return Retorna o nome do campo ID da tabela
     */
    @Override
    public String getCampoID() {
        String retorno;

        try {
            String sql = "";
            sql += "   SELECT ta.attname AS column_name "
                    + "  FROM pg_class bc, pg_class ic, pg_index i, "
                    + "       pg_attribute ta, pg_attribute ia "
                    + " WHERE bc.oid = i.indrelid AND ic.oid = i.indexrelid "
                    + "   AND ia.attrelid = i.indexrelid "
                    + "   AND ta.attrelid = bc.oid "
                    + "   AND bc.relname = lower('" + tabela + "') "
                    + "   AND ta.attrelid = i.indrelid "
                    + "   AND ta.attnum = i.indkey[ia.attnum-1] "
                    + " ORDER BY  column_name;";

            ResultSet rs = ConexoesDB.getInstance().getConnection()
                    .createStatement().executeQuery(sql);
            rs.next();

            retorno = rs.getString(1);
        } catch (SQLException SQLe) {
            retorno = "Erro pesquisar pelo campo ID da tabela: " + tabela + ": "
                    + SQLe;
        }

        return retorno;
    }

    public String sqlView() {
        String campos = "";
        for (int i = 0; i < camposTabela.size(); i++) {
            DadosDAO dadoDAO = camposTabela.get(i);

            if (dadoDAO != null) {
                campos += dadoDAO.getCampo();

                if (i < (camposTabela.size() - 1)) {
                    campos += ",";
                }
            }
        }

        String retornoSQL = "SELECT " + campos + " FROM " + tabela + addWere()
                + " ORDER BY " + camposTabela.get(0).getCampo() + ";";

        return retornoSQL;
    }

    public ArrayList popularLista() {
        ArrayList retorno = new ArrayList();

        // efetua consulta no banco e popula a lista
        try {

            String campos = "";
            for (int i = 0; i < camposTabela.size(); i++) {
                DadosDAO dadoDAO = camposTabela.get(i);

                if (dadoDAO != null) {
                    campos += dadoDAO.getCampo();

                    if (i < (camposTabela.size() - 1)) {
                        campos += ",";
                    }
                }
            }

            String sql = "SELECT " + campos + " FROM " + tabela + addWere()
                    + addOrderBy() + ";";
            ResultSet rs = ConexoesDB.getInstance().getConnection().
                    createStatement().executeQuery(sql);
            /**
             * TIPO_INTEGER = 0; TIPO_STRING = 1; TIPO_DATE = 2; TIPO_DOUBLE =
             * 3; TIPO_BOOLEAN = 4; TIPO_LONG = 5;
             */
            while (rs.next()) {
                for (DadosDAO dadoDAO : camposTabela) {
                    switch (dadoDAO.getTipo()) {
                        case DadosDAO.TIPO_INTEGER:
                            dadoDAO.setValor(String.valueOf(
                                    rs.getInt(dadoDAO.getCampo())));
                            break;
                        case DadosDAO.TIPO_STRING:
                            dadoDAO.setValor(rs.getString(dadoDAO.getCampo()));
                            break;
                        case DadosDAO.TIPO_DATE:
                            dadoDAO.setValor(String.valueOf(
                                    rs.getDate(dadoDAO.getCampo())));
                            break;
                        case DadosDAO.TIPO_DOUBLE:
                            dadoDAO.setValor(String.valueOf(
                                    rs.getDouble(dadoDAO.getCampo())));
                            break;
                        case DadosDAO.TIPO_BOOLEAN:
                            dadoDAO.setValor(String.valueOf(
                                    rs.getBoolean(dadoDAO.getCampo())));
                            break;
                        case DadosDAO.TIPO_LONG:
                            dadoDAO.setValor(String.valueOf(
                                    rs.getLong(dadoDAO.getCampo())));
                            break;
                    }

                    retorno.add(new DadosDAO(dadoDAO));
                }
            }
        } catch (SQLException SQLe) {
        }

        return retorno;
    }

    private String addOrderBy() {
        String retorno = " ORDER BY ";
        for (int i = 0; i < camposTabela.size(); i++) {
            DadosDAO dadoDAO = camposTabela.get(i);

            if (dadoDAO != null) {
                retorno += dadoDAO.getCampo();

                if (i < (camposTabela.size() - 1)) {
                    retorno += ",";
                }
            }
        }

        return retorno;
    }

    /**
     * PreencherLista.
     *
     * @author Saibel, Sergio Luis
     * @date 01/04/2017
     *
     * @revision 001.20170401 date 01/04/2017 author Saibel, Sergio Luís reason
     * Preencher uma lista com: nome do campo id(PK) da tabela, nome do campo
     * cod(FK) da tabela, uma descrição para substituir o campo cod em uma
     * consulta e o nome da tabela.
     */
    private void preencherLista() {
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_pessoa", "cod_pessoa", getCampo("nome||' '||sobrenome", "pessoas", "id_pessoa = cod_pessoa", true), "pessoas"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_pessoa", "cod_pessoa_fisica", getCampo("nome||' '||sobrenome", "pessoas", "id_pessoa = cod_pessoa_fisica", true), "pessoas"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_pessoa", "cod_pessoa_juridica", getCampo("nome||' '||sobrenome", "pessoas", "id_pessoa = cod_pessoa_juridica", true), "pessoas"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_pais", "cod_pais", getCampo("sigla||' - '||descricao", "paises", "id_pais = cod_pais", true), "paises"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_estado", "cod_estado", getCampo("sigla||' - '||descricao", "estados", "id_estado = cod_estado", true), "estados"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_cidade", "cod_cidade", getCampo("descricao", "cidades", "id_cidade = cod_cidade", true), "cidades"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_bairro", "cod_bairro", getCampo("descricao", "bairros", "id_bairro = cod_bairro", true), "bairros"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_tipo_endereco", "cod_tipo_endereco", getCampo("descricao", "tipos_enderecos", "id_tipo_endereco = cod_tipo_endereco", true), "tipos_enderecos"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_endereco", "cod_endereco", getCampo("descricao||' - '||cep||' - '||complemento", "enderecos", "id_endereco = cod_endereco", true), "enderecos"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_tipo_telefone", "cod_tipo_telefone", getCampo("descricao", "tipos_telefones", "id_tipo_telefone = cod_tipo_telefone", true), "tipos_telefones"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_telefone", "cod_telefone", getCampo("numero||' - '||ramal||' - '||contato", "telefones", "id_telefone = cod_telefone", true), "telefones"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_cliente", "cod_cliente", getCampo("nome||' '||sobrenome", "clientes, pessoas", "id_cliente = cod_cliente AND id_pessoa = cod_pessoa", true), "clientes"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_funcionario", "cod_funcionario", getCampo("nome||' '||sobrenome", "funcionarios, pessoas", "id_funcionario = cod_funcionario AND id_pessoa = cod_pessoa", true), "funcionarios"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_situacao", "cod_situacao", getCampo("descricao", "situacoes_pessoas", "id_situacao = cod_situacao", true), "situacoes_pessoas"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_situacao", "cod_situacao", getCampo("descricao", "situacoes_funcionarios", "id_situacao = cod_situacao", true), "situacoes_funcionarios"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_situacao", "cod_situacao", getCampo("descricao", "situacoes_clientes", "id_situacao = cod_situacao", true), "situacoes_clientes"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_funcao", "cod_funcao", getCampo("descricao", "funcoes", "id_funcao = cod_funcao", true), "funcoes"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_acao", "cod_acao", getCampo("descricao", "acoes", "id_acao = cod_acao", true), "acoes"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_usuario", "cod_usuario", getCampo("login", "usuarios", "id_usuario = cod_usuario", true), "usuarios"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_profissional", "cod_profissional", getCampo("nome||' '||sobrenome", "profissionais, pessoas", "id_profissional = cod_profissional AND id_pessoa = cod_pessoa", true), "profissionais"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_profissional", "cod_profissional_cadastro", getCampo("nome||' '||sobrenome", "profissionais, pessoas", "id_profissional = cod_profissional_cadastro AND id_pessoa = cod_pessoa", true), "profissionais"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_especializacao", "cod_especializacao", getCampo("descricao", "especializacoes", "id_especializacao = cod_especializacao", true), "especializacoes"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_plano_saude", "cod_plano_saude", getCampo("descricao", "planos_saude", "id_plano_saude = cod_plano_saude", true), "planos_saude"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_tipo_plano", "cod_tipo_plano", getCampo("planos_saude.descricao||' - '||tipos_planos.descricao", "tipos_planos, planos_saude", "planos_saude.id_plano_saude = tipos_planos.cod_plano_saude AND tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano", true), "tipos_planos"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_glossario_doenca", "cod_glossario_doenca", getCampo("descricao", "glossario_doencas", "id_glossario_doenca = cod_glossario_doenca", true), "glossario_doencas"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_medicamento", "cod_medicamento", getCampo("descricao", "medicamentos", "id_medicamento = cod_medicamento", true), "medicamentos"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_sintoma_apresentado", "cod_sintoma_apresentado", getCampo("descricao", "sintomas_apresentados", "id_sintoma_apresentado = cod_sintoma_apresentado", true), "sintomas_apresentados"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_prontuario", "cod_prontuario", getCampo("id_prontuario||' - '||data_consulta", "prontuarios", "id_prontuario = cod_prontuario", true), "prontuarios"));
        listaCamposDescricao.add(new CamposSubstituirDecricao("id_motivo_agenda", "cod_motivo_agenda", getCampo("descricao", "motivos_agendas", "id_motivo_agenda = cod_motivo_agenda", true), "motivos_agendas"));
    }

    /**
     * GetCampo.
     *
     * @author Saibel, Sergio Luis
     * @since date 01/04/2017
     *
     * revision 001.20170401 date 01/04/2017 author Saibel, Sergio Luís reason
     * Retornar um select capaz de trazer um ou mais campos do banco.
     *
     * @param campo nome do campo a ser trazido pelo select;
     * @param tabela nome da tabela a qual se deseja fazer a consulta. Caso haja
     * mais de uma tabela a primeira somente será considerada;
     * @param condicao condição para a consulta;
     * @param isAlias: Aliaa da tabela;
     * @return Valor do campo.
     */
    public String getCampo(String campo, String tabela, String condicao,
            boolean isAlias) {
        String retorno
                = "(SELECT " + campo
                + "   FROM " + tabela
                + "  WHERE " + condicao + ")";

        if (isAlias) {
            retorno += " AS ";

            if (tabela.indexOf(',') > 0) {
                retorno += tabela.substring(0, tabela.indexOf(','));
            } else {
                retorno += tabela;
            }
        }

        return retorno;
    }

    /**
     * AddCampo.
     *
     * @author Saibel, Sergio Luis
     * @since date 01/04/2017
     *
     * revision 001.20170401 date 01/04/2017 author Saibel, Sergio Luís reason
     * Adicionar um objeto a um vetor responsável pelo comandos CRUD no banco.
     *
     * @param dado Objeto que contenha: o nome do campo, descrição, tipo e
     * valor.
     */
    public void addCampo(DadosDAO dado) {
        camposTabela.add(dado);
    }

    /**
     * AddWhere.
     *
     * @author Saibel, Sergio Luis
     * @since date 01/04/2017
     *
     * revision 001.20170401 date 01/04/2017 author Saibel, Sergio Luís reason
     * Adicionar um objeto a um vetor responsável pela condição WHERE no banco.
     *
     * @param dado Objeto que contenha: o nome do campo, descrição, tipo e
     * valor.
     */
    public void addWhere(DadosDAO dado) {
        condicaoWhere.add(dado);
    }

    public void setCamposTabelaFormatados() {
        // Inclui os campos de descrição aos campos já existentes
        for (int i = 0; i < camposTabela.size(); i++) {
            DadosDAO dadoDAO = camposTabela.get(i);

            String strCampo = dadoDAO.getCampo();

            // Inclui os campos de descrição para os campos de código
            if (strCampo.indexOf('_') > 0) {
                if (strCampo.substring(0, strCampo.indexOf('_')).equals("cod")) {
                    for (CamposSubstituirDecricao campoSubstituirDecricao : listaCamposDescricao) {
                        if (strCampo.equals(
                                campoSubstituirDecricao.getNome_campo_cod())) {

                            // Inclui um novo campo a lista de campos da tabela,
                            // onde: 
                            //
                            //  * CAMPO = nome da tabela a qual se obtem a
                            // descição; 
                            //  * DESCRICAO = recebe uma descição padrão
                            // para que seja possível identificar o campo; 
                            //  * VALOR = o valor que o campo irá ter (para este
                            // tipo de campo o valor será um select em uma das 
                            // tabelas do banco) 
                            //  * TIPO = String 
                            //  * CONDICAO = String 
                            //  * ISCHAVE = false
                            camposTabela.add(++i, new DadosDAO(
                                    campoSubstituirDecricao.getNome_tabela(),
                                    DESCR_CAMPO_DESCR,
                                    campoSubstituirDecricao.getNome_campo_descricao(),
                                    DadosDAO.TIPO_STRING, DadosDAO.IS_IGUAL,
                                    DadosDAO.NAO_CHAVE));

                            // Sai do loop para não passar pos todos os itens da lista
                            break;
                        }
                    }
                }
            }
        }
    }

    public Object[] getCabecalhoTabela() {
        // Monta o cabecalho da tabela
        Object[] cabecalho = new Object[camposTabela.size()];
        for (int i = 0; i < camposTabela.size(); i++) {
            DadosDAO dadoDAO = camposTabela.get(i);

            /* 
             * Se o campo tiver a descição padrão então pega a descrição do campo
             * anterior menos a primeira palavra;
             */
            if (!dadoDAO.getDescricao().equals(DESCR_CAMPO_DESCR)) {
                cabecalho[i] = dadoDAO.getDescricao();
            } else {
                String descr = camposTabela.get(i - 1).getDescricao();

                cabecalho[i] = descr.substring(descr.indexOf(' ') + 1, descr.length());
            }
        }

        return cabecalho;
    }

    public int getQuantidadeRegistrosTabela() {
        int retorno = 0;
        ResultSet rs;

        // Cria matriz de acordo com nº de registros da tabela
        try {
            String sql
                    = " SELECT count(*) FROM " + tabela + addWere() + ";";

            rs = ConexoesDB.getInstance().getConnection()
                    .createStatement().executeQuery(sql);
            rs.next();

            retorno = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Erro ao consultar " + tabela + ": " + e);
        }

        return retorno;
    }

    public Object[][] getDadosTabela() {
        Object[][] retorno = null;
        ResultSet rs;

        // Efetua consulta no banco e popula a tabela
        try {

            String campos = "";
            for (int i = 0; i < camposTabela.size(); i++) {
                DadosDAO dadoDAO = camposTabela.get(i);

                // Se a descrição do campo for igual a padrão então pega o valor.
                if (dadoDAO != null) {
                    if (dadoDAO.getDescricao().equals(DESCR_CAMPO_DESCR)) {
                        campos += dadoDAO.getValor();
                    } else {
                        campos += dadoDAO.getCampo();
                    }

                    if (i < (camposTabela.size() - 1)) {
                        campos += ",";
                    }
                }
            }

            String sql = "SELECT " + campos + " FROM " + tabela + addWere()
                    + " ORDER BY " + camposTabela.get(0).getCampo() + ";";

            rs = ConexoesDB.getInstance().getConnection().
                    createStatement().executeQuery(sql);

            int lin = 0;
            while (rs.next()) {
                for (int i = 0; i < camposTabela.size(); i++) {
                    DadosDAO dadoDAO = camposTabela.get(i);

                    switch (dadoDAO.getTipo()) {
                        case DadosDAO.TIPO_INTEGER:
                            if (!dadoDAO.getCampo().equals("dia_semana")) {
                                retorno[lin][i] = rs.getInt(dadoDAO.getCampo());
                            } else {
                                retorno[lin][i]
                                        = Formatacao.DIAS_SEMANA_COMPL.get(
                                                rs.getInt(dadoDAO.getCampo()));
                            }
                            break;
                        case DadosDAO.TIPO_STRING:
                            retorno[lin][i] = rs.getString(dadoDAO.getCampo());
                            break;
                        case DadosDAO.TIPO_DATE:
                            retorno[lin][i] = rs.getDate(dadoDAO.getCampo());
                            break;
                        case DadosDAO.TIPO_DOUBLE:
                            retorno[lin][i] = rs.getDouble(dadoDAO.getCampo());
                            break;
                        case DadosDAO.TIPO_BOOLEAN:
                            if (rs.getBoolean(dadoDAO.getCampo())) {
                                retorno[lin][i]
                                        = new ImageIcon(getClass().getClassLoader().getResource("res/true.png"));
                            } else {
                                retorno[lin][i]
                                        = new ImageIcon(getClass().getClassLoader().getResource("res/false.png"));
                            }
                            break;
                        case DadosDAO.TIPO_LONG:
                            retorno[lin][i] = rs.getLong(dadoDAO.getCampo());
                            break;
                    }
                }

                lin++;
            }
        } catch (SQLException SQLe) {
            retorno = null;
        }
        return retorno;
    }

    /**
     * Popula a Grid JTable.
     *
     * @author Saibel, Sergio Luis
     * @since date 21/04/2017
     *
     * revision 001.20170421 date 21/04/2017 author Saibel, Sergio Luís reason
     * Substituir os códigos do dia da semana pela descrição.
     *
     * revision 001.20170401 date 01/04/2017 author Saibel, Sergio Luís reason
     * Popular um JTable com os dados da tabela.
     * @param jTable: Tabela
     * @return Retorna tabela populada.
     *
     */
    public String popularGridJTable(JTable jTable) {
        String retorno = null;

        // Inclui os campos de descrição aos campos já existentes
        setCamposTabelaFormatados();

        // Monta o cabecalho da tabela
        Object[] cabecalho = getCabecalhoTabela();

        ResultSet rs;
        Object[][] dadosTabela = null;

        // Cria matriz de acordo com nº de registros da tabela
        dadosTabela = new Object[getQuantidadeRegistrosTabela()][cabecalho.length];

        ArrayList<Integer> colImage = new ArrayList<>();

        // Efetua consulta no banco e popula a tabela
        try {

            String campos = "";
            for (int i = 0; i < camposTabela.size(); i++) {
                DadosDAO dadoDAO = camposTabela.get(i);

                // Se a descrição do campo for igual a padrão então pega o valor.
                if (dadoDAO != null) {
                    if (dadoDAO.getDescricao().equals(DESCR_CAMPO_DESCR)) {
                        campos += dadoDAO.getValor();
                    } else {
                        campos += dadoDAO.getCampo();
                    }

                    if (i < (camposTabela.size() - 1)) {
                        campos += ",";
                    }
                }
            }

            String sql = "SELECT " + campos + " FROM " + tabela + addWere()
                    + " ORDER BY " + camposTabela.get(0).getCampo() + ";";

            rs = ConexoesDB.getInstance().getConnection().
                    createStatement().executeQuery(sql);

            int lin = 0;
            while (rs.next()) {
                for (int i = 0; i < camposTabela.size(); i++) {
                    DadosDAO dadoDAO = camposTabela.get(i);

                    switch (dadoDAO.getTipo()) {
                        case DadosDAO.TIPO_INTEGER:
                            if (!dadoDAO.getCampo().equals("dia_semana")) {
                                dadosTabela[lin][i] = rs.getInt(dadoDAO.getCampo());
                            } else {
                                dadosTabela[lin][i]
                                        = Formatacao.DIAS_SEMANA_COMPL.get(
                                                rs.getInt(dadoDAO.getCampo()));
                            }
                            break;
                        case DadosDAO.TIPO_STRING:
                            dadosTabela[lin][i] = rs.getString(dadoDAO.getCampo());
                            break;
                        case DadosDAO.TIPO_DATE:
                            dadosTabela[lin][i] = rs.getDate(dadoDAO.getCampo());
                            break;
                        case DadosDAO.TIPO_DOUBLE:
                            dadosTabela[lin][i] = rs.getDouble(dadoDAO.getCampo());
                            break;
                        case DadosDAO.TIPO_BOOLEAN:
                            colImage.add(i);

                            if (rs.getBoolean(dadoDAO.getCampo())) {
                                dadosTabela[lin][i]
                                        = new ImageIcon(getClass().getClassLoader().getResource("res/true.png"));
                            } else {
                                dadosTabela[lin][i]
                                        = new ImageIcon(getClass().getClassLoader().getResource("res/false.png"));
                            }
                            break;
                        case DadosDAO.TIPO_LONG:
                            dadosTabela[lin][i] = rs.getLong(dadoDAO.getCampo());
                            break;
                    }
                }

                lin++;
            }
        } catch (SQLException SQLe) {
            retorno = "Problemas para popular tabela..."
                    + "Motivo : " + SQLe;
        }

        GridJTable.formatarTabela(jTable, dadosTabela, cabecalho, colImage);

        /**
         * Se a descrição do campo for igual a padrão então remove o campo para
         * este não ser duplicado em uma próxima consulta.
         */
        for (int i = 0; i < camposTabela.size(); i++) {
            DadosDAO dadoDAO = camposTabela.get(i);

            if (dadoDAO != null) {
                if (dadoDAO.getDescricao().equals(DESCR_CAMPO_DESCR)) {
                    camposTabela.remove(i);
                }
            }
        }

        return retorno;
    }

    /**
     * Próximo ID.
     *
     * @author Saibel, Sergio Luis
     * @since date 01/04/2017
     *
     * revision 001.20170401 date 01/04/2017 author Saibel, Sergio Luís reason
     * @param campoID: Campo ID na tabela
     * @return Retorna um numero que representa o próximo ID livre.
     */
    public int getProximoID(String campoID) {
        int retorno = -1;
        ResultSet rs;
        try {
            Statement st
                    = ConexoesDB.getInstance().getConnection().createStatement();

            String sql = "SELECT MAX(" + campoID + ") FROM " + tabela;
            rs = st.executeQuery(sql);
            rs.next();
            retorno = rs.getInt(1) + 1;
        } catch (SQLException SQLe) {
            System.out.println(SQLe);
        }

        return retorno;
    }

    /**
     * Add Where.
     *
     * @author Saibel, Sergio Luis
     * @date 01/04/2017
     *
     * @revision 001.20170401 date 01/04/2017 author Saibel, Sergio Luís reason
     * @param isCampoCompleto: se o campo deve ser pesquisado por completo.
     * @return Retorna condições WHERE.
     */
    private String addWere() {
        String retorno = "";
        String operador = "";

        if (condicaoWhere.size() > 0) {
            retorno += " WHERE ";
            for (int i = 0; i < condicaoWhere.size(); i++) {
                DadosDAO dadoDAO = condicaoWhere.get(i);

                switch (dadoDAO.getCondicao()) {
                    case DadosDAO.IS_IGUAL:
                        operador = " = ";
                        break;
                    case DadosDAO.IS_DIFERENTE:
                        operador = " <> ";
                        break;
                    case DadosDAO.IS_MAIOR:
                        operador = " > ";
                        break;
                    case DadosDAO.IS_MENOR:
                        operador = " < ";
                        break;
                    case DadosDAO.IS_MAIOR_IGUAL:
                        operador = " >= ";
                        break;
                    case DadosDAO.IS_MENOR_IGUAL:
                        operador = " <= ";
                        break;
                }

                retorno += dadoDAO.getCampo();

                if ((dadoDAO.getTipo() == DadosDAO.TIPO_INTEGER)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_DOUBLE)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_BOOLEAN)
                        || (dadoDAO.getTipo() == DadosDAO.TIPO_LONG)) {
                    retorno += operador + dadoDAO.getValor();
                } else {
                    if (dadoDAO.getTipo() == DadosDAO.TIPO_DATE) {
                        retorno += operador + "'" + dadoDAO.getValor() + "'";
                    } else {
                        if (dadoDAO.getTipo() == DadosDAO.TIPO_STRING) {
                            if (dadoDAO.getCondicaoAtributo() == DadosDAO.COMPLETO) {
                                retorno += operador + "'" + dadoDAO.getValor() + "'";
                            } else {
                                retorno += " iLIKE '%" + dadoDAO.getValor() + "%'";
                            }
                        }
                    }
                }

                if (i < (condicaoWhere.size() - 1)) {
                    retorno += " AND ";
                }
            }
        }

        return retorno;
    }

    /**
     * @return the tabela
     */
    public String getTabela() {
        return tabela;
    }

    /**
     * @param tabela the tabela to set
     */
    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    /**
     * @return the camposTabela
     */
    public ArrayList<DadosDAO> getCamposTabela() {
        return camposTabela;
    }

    /**
     * @return the condicaoWhere
     */
    public ArrayList<DadosDAO> getCondicaoWhere() {
        return condicaoWhere;
    }
}
