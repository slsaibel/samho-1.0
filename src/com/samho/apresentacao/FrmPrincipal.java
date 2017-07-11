/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samho.apresentacao;

import com.samho.apresentacao.rel.IFrmRelPessoasFisicas;
import com.samho.negocio.Acoes;
import com.samho.negocio.AcoesUsuarios;
import com.samho.negocio.Agendas;
import com.samho.negocio.Bairros;
import com.samho.negocio.Cidades;
import com.samho.negocio.Clientes;
import com.samho.negocio.ClientesTiposPlanos;
import com.samho.negocio.Enderecos;
import com.samho.negocio.Especializacoes;
import com.samho.negocio.Estados;
import com.samho.negocio.Funcionarios;
import com.samho.negocio.Funcoes;
import com.samho.negocio.GlossarioDoencas;
import com.samho.negocio.Medicamentos;
import com.samho.negocio.MotivosAgendas;
import com.samho.negocio.Paises;
import com.samho.negocio.Pessoas;
import com.samho.negocio.PessoasEspecializacoes;
import com.samho.negocio.PlanosSaude;
import com.samho.negocio.Principal;
import com.samho.negocio.Profissionais;
import com.samho.negocio.ProfissionaisPlanosSaude;
import com.samho.negocio.ProfissionaisTurnos;
import com.samho.negocio.Prontuarios;
import com.samho.negocio.SintomasApresentados;
import com.samho.negocio.SituacoesClientes;
import com.samho.negocio.SituacoesFuncionarios;
import com.samho.negocio.SituacoesPessoas;
import com.samho.negocio.Telefones;
import com.samho.negocio.TiposEnderecos;
import com.samho.negocio.TiposPlanosSaude;
import com.samho.negocio.TiposTelefones;
import com.samho.negocio.Usuarios;
import com.samho.negocio.Aparencias;
import com.samho.util.Util;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * Classe Principal da Aplicação.
 *
 * @author Saibel, Sergio Luis
 * @since  date 30/03/2017
 *
 * @version  revision 001.20170330 date 30/03/2017 author Saibel, Sérgio Luis reason
 * Iniciar a aplicação.
 */
public class FrmPrincipal extends javax.swing.JFrame {

    /**
     * @return the mnuBar
     */
    public javax.swing.JMenuBar getMnuBar() {
        return mnuBar;
    }

    /**
     * Creates new form FrmPrincipal
     * @throws javax.swing.UnsupportedLookAndFeelException    
     */
    public FrmPrincipal() throws UnsupportedLookAndFeelException {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
    }
    
    /**
     * Carrega as aparências da tela
     */
    private void setAparenciaPage() {
        final Aparencias aparencias = new Aparencias();
        aparencias.carregarPropriedades();
        aparencias.aplicarAparenciaImagemFundoJLabel(lblImagemFundo);
        aparencias.aplicarAparenciaLookAndFeel(this);
    }
    
    private void autenticarUsuário() {
        verificarPermissoes();
    }
    
    /**
     * Verifica as permissões do  usuário logado
     */
    private void verificarPermissoes(){
        int contador = 0;
        for (AcoesUsuarios permissoe : Principal.usuario.getPermissoes()) {
            contador++;
            
            AcoesUsuarios acoesUsuario = new AcoesUsuarios(permissoe);
            
            boolean incluir = acoesUsuario.isIncluir();
            boolean alterar = acoesUsuario.isAlterar();
            boolean excluir = acoesUsuario.isExcluir();
            boolean consultar = acoesUsuario.isConsultar();
            
            switch ( contador ) {
                case Principal.PESSOAS_FISICAS: 
                    mnuConsPessoasFisicas.setEnabled(consultar);
                    mnuNewPessoaFisica.setEnabled(incluir);
                    mnuEditPessoaFisica.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.PESSOAS_JURIDICAS: 
                    mnuConsPessoasJuridicas.setEnabled(consultar);
                    mnuNewPessoaJuridica.setEnabled(incluir);
                    mnuEditPessoaJuridica.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.PESSOAS:
                    mnuConsPessoas.setEnabled(consultar);
                    mnuNewPessoas.setEnabled(incluir);
                    mnuEditPessoas.setEnabled(consultar && (alterar||excluir));
                    break;   
                case Principal.PESSOAS_ESPECIALIZACOES: 
                    /**
                     * Ação exclusiva para o administrador
                     * Se não for administrador não mostra o item de menu
                     */
                    if (Principal.usuario.getIdUsuario() == 0) {
                        mnuConsEspecializacoesPessoas.setEnabled(consultar);
                        mnuNewEspecializacaoPessoa.setEnabled(incluir);
                        mnuEditEspecializacaoPessoa.setEnabled(consultar && (alterar||excluir));
                    } else {
                        mnuConsEspecializacoesPessoas.setVisible(false);
                        mnuNewEspecializacaoPessoa.setVisible(false);
                        mnuEditEspecializacaoPessoa.setVisible(false);
                    }
                    break;
                case Principal.SITUACOES_PESSOAS: 
                    mnuConsSituacoesPessoas.setEnabled(consultar);
                    mnuNewSituacaoPessoas.setEnabled(incluir);
                    mnuEditSituacaoPessoa.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.ESPECIALIZACOES: 
                    mnuConsEspecializacoes.setEnabled(consultar);
                    mnuNewEspecializacao.setEnabled(incluir);
                    mnuEditEspecializacao.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.FUNCIONARIOS: 
                    mnuConsFuncionario.setEnabled(consultar);
                    mnuNewFuncionario.setEnabled(incluir);
                    mnuEditFuncionario.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.SITUACOES_FUNCIONARIOS: 
                    mnuConsSituacoesFuncionarios.setEnabled(consultar);
                    mnuNewSituacaoFuncionarios.setEnabled(incluir);
                    mnuEditSituacaoFuncionarios.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.FUNCOES: 
                    mnuConsFuncoes.setEnabled(consultar);
                    mnuNewFuncao.setEnabled(incluir);
                    mnuEditFuncao.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.PROFISSIONAIS: 
                    mnuConsProfissional.setEnabled(consultar);
                    mnuNewProfissional.setEnabled(incluir);
                    mnuEditProfissional.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.PROFISSIONAIS_TURNOS:
                    /**
                     * Ação exclusiva para o administrador
                     * Se não for administrador não mostra o item de menu
                     */
                    if (Principal.usuario.getIdUsuario() == 0) {
                        mnuConsProfissionaisTurnos.setEnabled(consultar);
                        mnuNewProfissionalTurnos.setEnabled(incluir);
                        mnuEditProfissionalTurnos.setEnabled(consultar && (alterar||excluir));
                    } else {
                        mnuConsProfissionaisTurnos.setVisible(false);
                        mnuNewProfissionalTurnos.setVisible(false);
                        mnuEditProfissionalTurnos.setVisible(false);
                    }
                    break;
                case Principal.CLIENTES: 
                    mnuConsCliente.setEnabled(consultar);
                    mnuNewCliente.setEnabled(incluir);
                    mnuEditCliente.setEnabled(consultar && (alterar||excluir));
                    break; 
                case Principal.SITUACOES_CLIENTES: 
                    mnuConsSituacoesClientes.setEnabled(consultar);
                    mnuNewSituacaoCliente.setEnabled(incluir);
                    mnuEditSituacaoCliente.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.USUARIOS: 
                    mnuConsUsuario.setEnabled(consultar);
                    mnuNewUsuario.setEnabled(incluir);
                    mnuEditUsuario.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.ACOES_USUARIOS:
                    /**
                     * Ação exclusiva para o administrador
                     * Se não for administrador não mostra o item de menu
                     */
                    if (Principal.usuario.getIdUsuario() == 0) {
                        mnuConsUsuariosAcoes.setEnabled(consultar);
                        mnuNewUsuarioAcoes.setEnabled(incluir);
                        mnuEditUsuarioAcoes.setEnabled(consultar && (alterar||excluir));
                    } else {
                        mnuConsUsuariosAcoes.setVisible(false);
                        mnuNewUsuarioAcoes.setVisible(false);
                        mnuEditUsuarioAcoes.setVisible(false);
                    }
                    break;
                case Principal.ACOES: 
                    /**
                     * Ação exclusiva para o administrador
                     * Se não for administrador não mostra o item de menu
                     */
                    if (Principal.usuario.getIdUsuario() == 0) {
                        mnuConsAcoes.setEnabled(consultar);
                        mnuNewAcao.setEnabled(incluir);
                        mnuEditAcao.setEnabled(consultar && (alterar||excluir));
                    } else {
                        mnuConsAcoes.setVisible(false);
                        mnuNewAcao.setVisible(false);
                        mnuEditAcao.setVisible(false);
                    }
                    break;
                case Principal.ENDERECOS: 
                    mnuConsEndereco.setEnabled(consultar);
                    mnuNewEndereco.setEnabled(incluir);
                    mnuEditEndereco.setEnabled(consultar && (alterar||excluir));
                    break;                   
                case Principal.TIPOS_ENDERECOS: 
                    mnuConsEnderecosTipos.setEnabled(consultar);
                    mnuNewEnderecoTipo.setEnabled(incluir);
                    mnuEditEnderecoTipo.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.BAIRROS: 
                    mnuConsBairros.setEnabled(consultar);
                    mnuNewBairro.setEnabled(incluir);
                    mnuEditBairro.setEnabled(consultar && (alterar||excluir));
                    break;
                 case Principal.CIDADES: 
                    mnuConsCidades.setEnabled(consultar);
                    mnuNewCidade.setEnabled(incluir);
                    mnuEditCidade.setEnabled(consultar && (alterar||excluir));
                    break;                   
                 case Principal.ESTADOS: 
                    mnuConsEstados.setEnabled(consultar);
                    mnuNewEstado.setEnabled(incluir);
                    mnuEditEstado.setEnabled(consultar && (alterar||excluir));
                    break;                   
                case Principal.PAISES: 
                    mnuConsPaises.setEnabled(consultar);
                    mnuNewPais.setEnabled(incluir);
                    mnuEditPais.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.TELEFONES: 
                    mnuConsTelefone.setEnabled(consultar);
                    mnuNewTelefone.setEnabled(incluir);
                    mnuEditTelefone.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.TIPOS_TELEFONES: 
                    mnuConsTelefonesTipos.setEnabled(consultar);
                    mnuNewTelefoneTipo.setEnabled(incluir);
                    mnuEditTelefoneTipo.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.TIPOS_PLANOS: 
                    mnuConsTiposPalanosSaude.setEnabled(consultar);
                    mnuNewTipoPlanoSaude.setEnabled(incluir);
                    mnuEditTipoPlanoSaude.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.CLIENTES_TIPOS_PLANOS:
                    /**
                     * Ação exclusiva para o administrador
                     * Se não for administrador não mostra o item de menu
                     */
                    if (Principal.usuario.getIdUsuario() == 0) {
                        mnuConsTiposPlanosSaudeCliente.setEnabled(consultar);
                        mnuNewTipoPlanoSaudeCliente.setEnabled(incluir);
                        mnuEditTipoPlanoSaudeCliente.setEnabled(consultar && (alterar||excluir));
                    } else {
                        mnuConsTiposPlanosSaudeCliente.setEnabled(false);
                        mnuNewTipoPlanoSaudeCliente.setEnabled(false);
                        mnuEditTipoPlanoSaudeCliente.setEnabled(false);    
                    }
                    break;
                case Principal.PLANOS_SAUDE: 
                    mnuConsPlanoSaude.setEnabled(consultar);
                    mnuNewPlanoSaude.setEnabled(incluir);
                    mnuEditPlanoSaude.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.PROFISSIONAIS_PLANOS_SAUDE: 
                    /**
                     * Ação exclusiva para o administrador
                     * Se não for administrador não mostra o item de menu
                     */
                    if (Principal.usuario.getIdUsuario() == 0) {
                        mnuConsProfissionaisPlanosSaude.setEnabled(consultar);
                        mnuNewProfissionalPlanoSaude.setEnabled(incluir);
                        mnuEditProfissionalPlanoSaude.setEnabled(consultar && (alterar||excluir));
                    } else {
                        mnuConsProfissionaisPlanosSaude.setEnabled(false);
                        mnuNewProfissionalPlanoSaude.setEnabled(false);
                        mnuEditProfissionalPlanoSaude.setEnabled(false);    
                    }
                    break;
                case Principal.PRONTUARIOS: 
                    mnuConsProntuarios.setEnabled(consultar);
                    mnuNewProntuario.setEnabled(incluir);
                    mnuEditProntuario.setEnabled(consultar && (alterar||excluir));
                    break;
                case Principal.PRONTUARIOS_SINTOMAS_APRESENTADOS: 
                    // Não possui acesso direto
                    break;
                case Principal.PRONTUARIOS_MEDICAMENTOS: 
                    // Não possui acesso direto
                    break;
                case Principal.SINTOMAS_APRESENTADOS: 
                    // Não possui acesso direto
                    break;
                case Principal.GLOSSARIO_DOENCAS_SINTOMAS_APRESENTADOS: 
                    // Não possui acesso direto
                    break;
                case Principal.GLOSSARIO_DOENCAS:
                    /**
                     * Este é um item de menu é único
                     */
                    mnuGlossarioDoencas.setEnabled(incluir||alterar||excluir||consultar);
                    break;
                case Principal.MEDICAMENTOS: 
                    /**
                     * Este é um item de menu é único
                     */
                    mnuMedicamentos.setEnabled(incluir||alterar||excluir||consultar);
                    break;
                case Principal.GLOSSARIO_DOENCA_MEDICAMENTOS: 
                    // Não possui acesso direto
                    break;
                case Principal.MOTIVOS_AGENDAS: 
                    /**
                     * Este é um item de menu é único e somente acessivel pelo administrador
                     */
                    if (Principal.usuario.getIdUsuario() == 0) {
                        mnuConsMotivosAgendas.setEnabled(incluir||alterar||excluir||consultar);
                    } else {
                        mnuConsMotivosAgendas.setEnabled(false);
                    }
                    break;
                case Principal.AGENDAS: 
                    /**
                     * Este é um item de menu é único
                     */
                    mnuConsAgendas.setEnabled(incluir||alterar||excluir||consultar);
                    break;                  
                default:
                    break;
            }
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descPane = new javax.swing.JDesktopPane();
        lblImagemFundo = new javax.swing.JLabel();
        mnuBar = new javax.swing.JMenuBar();
        mnuSAMHO = new javax.swing.JMenu();
        mnuNovo = new javax.swing.JMenu();
        mnuNewPessoas = new javax.swing.JMenu();
        mnuNewPessoaFisica = new javax.swing.JMenuItem();
        mnuNewPessoaJuridica = new javax.swing.JMenuItem();
        jSeparator1NewPessoas = new javax.swing.JPopupMenu.Separator();
        mnuNewSituacaoPessoas = new javax.swing.JMenuItem();
        mnuNewEspecializacaoPessoa = new javax.swing.JMenuItem();
        jSeparator1New = new javax.swing.JPopupMenu.Separator();
        mnuNewClientes = new javax.swing.JMenu();
        mnuNewCliente = new javax.swing.JMenuItem();
        jSeparator1NewClientes = new javax.swing.JPopupMenu.Separator();
        mnuNewTipoPlanoSaudeCliente = new javax.swing.JMenuItem();
        mnuNewSituacaoCliente = new javax.swing.JMenuItem();
        mnuNewFuncionarios = new javax.swing.JMenu();
        mnuNewFuncionario = new javax.swing.JMenuItem();
        jSeparator1NewFuncionarios = new javax.swing.JPopupMenu.Separator();
        mnuNewSituacaoFuncionarios = new javax.swing.JMenuItem();
        mnuNewProfissionais = new javax.swing.JMenu();
        mnuNewProfissional = new javax.swing.JMenuItem();
        jSeparator1NewProfissionais = new javax.swing.JPopupMenu.Separator();
        mnuNewProfissionalTurnos = new javax.swing.JMenuItem();
        mnuNewProfissionalPlanoSaude = new javax.swing.JMenuItem();
        jSeparator2New = new javax.swing.JPopupMenu.Separator();
        mnuNewUsuarios = new javax.swing.JMenu();
        mnuNewUsuario = new javax.swing.JMenuItem();
        jSeparator1NewUsuarios = new javax.swing.JPopupMenu.Separator();
        mnuNewAcao = new javax.swing.JMenuItem();
        mnuNewUsuarioAcoes = new javax.swing.JMenuItem();
        jSeparator3New = new javax.swing.JPopupMenu.Separator();
        mnuNewEnderecos = new javax.swing.JMenu();
        mnuNewEndereco = new javax.swing.JMenuItem();
        jSeparator1NewEnderecos = new javax.swing.JPopupMenu.Separator();
        mnuNewEnderecoTipo = new javax.swing.JMenuItem();
        jSeparator2NewEnderecos = new javax.swing.JPopupMenu.Separator();
        mnuNewBairro = new javax.swing.JMenuItem();
        mnuNewCidade = new javax.swing.JMenuItem();
        mnuNewEstado = new javax.swing.JMenuItem();
        mnuNewPais = new javax.swing.JMenuItem();
        mnuNewTelefones = new javax.swing.JMenu();
        mnuNewTelefone = new javax.swing.JMenuItem();
        jSeparator1NewTelefones = new javax.swing.JPopupMenu.Separator();
        mnuNewTelefoneTipo = new javax.swing.JMenuItem();
        jSeparator4New = new javax.swing.JPopupMenu.Separator();
        mnuNewEspecializacao = new javax.swing.JMenuItem();
        mnuNewFuncao = new javax.swing.JMenuItem();
        mnuNewPlanosSaude = new javax.swing.JMenu();
        mnuNewPlanoSaude = new javax.swing.JMenuItem();
        jSeparator1NewPlanosSaude = new javax.swing.JPopupMenu.Separator();
        mnuNewTipoPlanoSaude = new javax.swing.JMenuItem();
        mnuNewProntuario = new javax.swing.JMenuItem();
        mnuConsultar = new javax.swing.JMenu();
        mnuConsPessoas = new javax.swing.JMenu();
        mnuConsPessoasFisicas = new javax.swing.JMenuItem();
        mnuConsPessoasJuridicas = new javax.swing.JMenuItem();
        jSeparator1ConsPessoas = new javax.swing.JPopupMenu.Separator();
        mnuConsSituacoesPessoas = new javax.swing.JMenuItem();
        mnuConsEspecializacoesPessoas = new javax.swing.JMenuItem();
        jSeparator1Cons = new javax.swing.JPopupMenu.Separator();
        mnuConsClientes = new javax.swing.JMenu();
        mnuConsCliente = new javax.swing.JMenuItem();
        jSeparator1ConsClientes = new javax.swing.JPopupMenu.Separator();
        mnuConsTiposPlanosSaudeCliente = new javax.swing.JMenuItem();
        mnuConsSituacoesClientes = new javax.swing.JMenuItem();
        mnuConsFuncionaruios = new javax.swing.JMenu();
        mnuConsFuncionario = new javax.swing.JMenuItem();
        jSeparator1ConsFuncionarios = new javax.swing.JPopupMenu.Separator();
        mnuConsSituacoesFuncionarios = new javax.swing.JMenuItem();
        mnuConsProfissionais = new javax.swing.JMenu();
        mnuConsProfissional = new javax.swing.JMenuItem();
        jSeparator1ConsProfissionais = new javax.swing.JPopupMenu.Separator();
        mnuConsProfissionaisTurnos = new javax.swing.JMenuItem();
        mnuConsProfissionaisPlanosSaude = new javax.swing.JMenuItem();
        jSeparator2Cons = new javax.swing.JPopupMenu.Separator();
        mnuConsUsuarios = new javax.swing.JMenu();
        mnuConsUsuario = new javax.swing.JMenuItem();
        jSeparator1ConsUsuarios = new javax.swing.JPopupMenu.Separator();
        mnuConsUsuariosAcoes = new javax.swing.JMenuItem();
        mnuConsAcoes = new javax.swing.JMenuItem();
        jSeparator3Cons = new javax.swing.JPopupMenu.Separator();
        mnuConsEnderecos = new javax.swing.JMenu();
        mnuConsEndereco = new javax.swing.JMenuItem();
        jSeparator1ConsEnderecos = new javax.swing.JPopupMenu.Separator();
        mnuConsEnderecosTipos = new javax.swing.JMenuItem();
        jSeparator2ConsEnderecos = new javax.swing.JPopupMenu.Separator();
        mnuConsBairros = new javax.swing.JMenuItem();
        mnuConsCidades = new javax.swing.JMenuItem();
        mnuConsEstados = new javax.swing.JMenuItem();
        mnuConsPaises = new javax.swing.JMenuItem();
        mnuConsTelefones = new javax.swing.JMenu();
        mnuConsTelefone = new javax.swing.JMenuItem();
        jSeparator1ConsTelefones = new javax.swing.JPopupMenu.Separator();
        mnuConsTelefonesTipos = new javax.swing.JMenuItem();
        jSeparator4Cons = new javax.swing.JPopupMenu.Separator();
        mnuConsEspecializacoes = new javax.swing.JMenuItem();
        mnuConsFuncoes = new javax.swing.JMenuItem();
        mnuConsPlanosSaude = new javax.swing.JMenu();
        mnuConsPlanoSaude = new javax.swing.JMenuItem();
        jSeparator1ConsPlanosSaude = new javax.swing.JPopupMenu.Separator();
        mnuConsTiposPalanosSaude = new javax.swing.JMenuItem();
        mnuConsProntuarios = new javax.swing.JMenuItem();
        jSeparator1Arquivo = new javax.swing.JPopupMenu.Separator();
        mnuPesquisar = new javax.swing.JMenu();
        mnuGlossarioDoencas = new javax.swing.JMenuItem();
        mnuMedicamentos = new javax.swing.JMenuItem();
        mnuSintomasApresentados = new javax.swing.JMenuItem();
        mnuAgendas = new javax.swing.JMenu();
        mnuConsAgendas = new javax.swing.JMenuItem();
        jSeparator1Agendas = new javax.swing.JPopupMenu.Separator();
        mnuConsMotivosAgendas = new javax.swing.JMenuItem();
        jSeparator2Arquivo = new javax.swing.JPopupMenu.Separator();
        mnuSair = new javax.swing.JMenuItem();
        mnuEditar = new javax.swing.JMenu();
        mnuEditPessoas = new javax.swing.JMenu();
        mnuEditPessoaFisica = new javax.swing.JMenuItem();
        mnuEditPessoaJuridica = new javax.swing.JMenuItem();
        jSeparator1EditPessoas = new javax.swing.JPopupMenu.Separator();
        mnuEditSituacaoPessoa = new javax.swing.JMenuItem();
        mnuEditEspecializacaoPessoa = new javax.swing.JMenuItem();
        jSeparator1Edit = new javax.swing.JPopupMenu.Separator();
        mnuEditClientes = new javax.swing.JMenu();
        mnuEditCliente = new javax.swing.JMenuItem();
        jSeparator1EditClientes = new javax.swing.JPopupMenu.Separator();
        mnuEditTipoPlanoSaudeCliente = new javax.swing.JMenuItem();
        mnuEditSituacaoCliente = new javax.swing.JMenuItem();
        mnuEditFuncionarios = new javax.swing.JMenu();
        mnuEditFuncionario = new javax.swing.JMenuItem();
        jSeparator1EditFuncionarios = new javax.swing.JPopupMenu.Separator();
        mnuEditSituacaoFuncionarios = new javax.swing.JMenuItem();
        mnuEditProfissionais = new javax.swing.JMenu();
        mnuEditProfissional = new javax.swing.JMenuItem();
        jSeparator1EditProfissionais = new javax.swing.JPopupMenu.Separator();
        mnuEditProfissionalTurnos = new javax.swing.JMenuItem();
        mnuEditProfissionalPlanoSaude = new javax.swing.JMenuItem();
        jSeparator2Edit = new javax.swing.JPopupMenu.Separator();
        mnuEditUsuarios = new javax.swing.JMenu();
        mnuEditUsuario = new javax.swing.JMenuItem();
        jSeparator1EditUsuarios = new javax.swing.JPopupMenu.Separator();
        mnuEditUsuarioAcoes = new javax.swing.JMenuItem();
        mnuEditAcao = new javax.swing.JMenuItem();
        jSeparator3Edit = new javax.swing.JPopupMenu.Separator();
        mnuEditEnderecos = new javax.swing.JMenu();
        mnuEditEndereco = new javax.swing.JMenuItem();
        jSeparator1EditEnderecos = new javax.swing.JPopupMenu.Separator();
        mnuEditEnderecoTipo = new javax.swing.JMenuItem();
        jSeparator2EditEnderecos = new javax.swing.JPopupMenu.Separator();
        mnuEditBairro = new javax.swing.JMenuItem();
        mnuEditCidade = new javax.swing.JMenuItem();
        mnuEditEstado = new javax.swing.JMenuItem();
        mnuEditPais = new javax.swing.JMenuItem();
        mnuEditTelefones = new javax.swing.JMenu();
        mnuEditTelefone = new javax.swing.JMenuItem();
        jSeparator1EditTelefones = new javax.swing.JPopupMenu.Separator();
        mnuEditTelefoneTipo = new javax.swing.JMenuItem();
        jSeparator4Edit = new javax.swing.JPopupMenu.Separator();
        mnuEditEspecializacao = new javax.swing.JMenuItem();
        mnuEditFuncao = new javax.swing.JMenuItem();
        mnuEditPlanosSaude = new javax.swing.JMenu();
        mnuEditPlanoSaude = new javax.swing.JMenuItem();
        jSeparator1EditPlanosSaude = new javax.swing.JPopupMenu.Separator();
        mnuEditTipoPlanoSaude = new javax.swing.JMenuItem();
        mnuEditProntuario = new javax.swing.JMenuItem();
        mnuFerramentas = new javax.swing.JMenu();
        mnuEditAparencia = new javax.swing.JMenuItem();
        mnuEditBackup = new javax.swing.JMenuItem();
        mnuRelatorios = new javax.swing.JMenu();
        mnuRelPessoasFisicas = new javax.swing.JMenuItem();
        mnuAjuda = new javax.swing.JMenu();
        mnuSobreSANHO = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("<< Sistema de Agendamento Medico Hospitalar - SAMHO >> Versão: 1.0");
        setPreferredSize(null);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        lblImagemFundo.setToolTipText("");

        descPane.setLayer(lblImagemFundo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout descPaneLayout = new javax.swing.GroupLayout(descPane);
        descPane.setLayout(descPaneLayout);
        descPaneLayout.setHorizontalGroup(
            descPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagemFundo, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
        );
        descPaneLayout.setVerticalGroup(
            descPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagemFundo, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
        );

        mnuSAMHO.setText("SAMHO");

        mnuNovo.setText("Novo Cadastro");

        mnuNewPessoas.setText("Pessoas");

        mnuNewPessoaFisica.setText("Pessoa Física");
        mnuNewPessoaFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewPessoaFisicaActionPerformed(evt);
            }
        });
        mnuNewPessoas.add(mnuNewPessoaFisica);

        mnuNewPessoaJuridica.setText("Pessoa Jurídica");
        mnuNewPessoaJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewPessoaJuridicaActionPerformed(evt);
            }
        });
        mnuNewPessoas.add(mnuNewPessoaJuridica);
        mnuNewPessoas.add(jSeparator1NewPessoas);

        mnuNewSituacaoPessoas.setText("Situação de Pessoas");
        mnuNewSituacaoPessoas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewSituacaoPessoasActionPerformed(evt);
            }
        });
        mnuNewPessoas.add(mnuNewSituacaoPessoas);

        mnuNewEspecializacaoPessoa.setText("Pessoa - Especialização");
        mnuNewEspecializacaoPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewEspecializacaoPessoaActionPerformed(evt);
            }
        });
        mnuNewPessoas.add(mnuNewEspecializacaoPessoa);

        mnuNovo.add(mnuNewPessoas);
        mnuNovo.add(jSeparator1New);

        mnuNewClientes.setText("Clientes");

        mnuNewCliente.setText("Cliente");
        mnuNewCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewClienteActionPerformed(evt);
            }
        });
        mnuNewClientes.add(mnuNewCliente);
        mnuNewClientes.add(jSeparator1NewClientes);

        mnuNewTipoPlanoSaudeCliente.setText("Plano de Saúde do Cliente");
        mnuNewTipoPlanoSaudeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewTipoPlanoSaudeClienteActionPerformed(evt);
            }
        });
        mnuNewClientes.add(mnuNewTipoPlanoSaudeCliente);

        mnuNewSituacaoCliente.setText("Situação de Clientes");
        mnuNewSituacaoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewSituacaoClienteActionPerformed(evt);
            }
        });
        mnuNewClientes.add(mnuNewSituacaoCliente);

        mnuNovo.add(mnuNewClientes);

        mnuNewFuncionarios.setText("Funcionários");

        mnuNewFuncionario.setText("Funcionário");
        mnuNewFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewFuncionarioActionPerformed(evt);
            }
        });
        mnuNewFuncionarios.add(mnuNewFuncionario);
        mnuNewFuncionarios.add(jSeparator1NewFuncionarios);

        mnuNewSituacaoFuncionarios.setText("Situação do Funcionário");
        mnuNewSituacaoFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewSituacaoFuncionariosActionPerformed(evt);
            }
        });
        mnuNewFuncionarios.add(mnuNewSituacaoFuncionarios);

        mnuNovo.add(mnuNewFuncionarios);

        mnuNewProfissionais.setText("Profissionais");

        mnuNewProfissional.setText("Profissional");
        mnuNewProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewProfissionalActionPerformed(evt);
            }
        });
        mnuNewProfissionais.add(mnuNewProfissional);
        mnuNewProfissionais.add(jSeparator1NewProfissionais);

        mnuNewProfissionalTurnos.setText("Profissinal - Turnos");
        mnuNewProfissionalTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewProfissionalTurnosActionPerformed(evt);
            }
        });
        mnuNewProfissionais.add(mnuNewProfissionalTurnos);

        mnuNewProfissionalPlanoSaude.setText("Profissional - Planos de Saúde");
        mnuNewProfissionalPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewProfissionalPlanoSaudeActionPerformed(evt);
            }
        });
        mnuNewProfissionais.add(mnuNewProfissionalPlanoSaude);

        mnuNovo.add(mnuNewProfissionais);
        mnuNovo.add(jSeparator2New);

        mnuNewUsuarios.setText("Usuários");

        mnuNewUsuario.setText("Usuário");
        mnuNewUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewUsuarioActionPerformed(evt);
            }
        });
        mnuNewUsuarios.add(mnuNewUsuario);
        mnuNewUsuarios.add(jSeparator1NewUsuarios);

        mnuNewAcao.setText("Ação");
        mnuNewAcao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewAcaoActionPerformed(evt);
            }
        });
        mnuNewUsuarios.add(mnuNewAcao);

        mnuNewUsuarioAcoes.setText("Usuário - Ações");
        mnuNewUsuarioAcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewUsuarioAcoesActionPerformed(evt);
            }
        });
        mnuNewUsuarios.add(mnuNewUsuarioAcoes);

        mnuNovo.add(mnuNewUsuarios);
        mnuNovo.add(jSeparator3New);

        mnuNewEnderecos.setText("Endereços");

        mnuNewEndereco.setText("Endereço");
        mnuNewEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewEnderecoActionPerformed(evt);
            }
        });
        mnuNewEnderecos.add(mnuNewEndereco);
        mnuNewEnderecos.add(jSeparator1NewEnderecos);

        mnuNewEnderecoTipo.setText("Endereço - Tipos");
        mnuNewEnderecoTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewEnderecoTipoActionPerformed(evt);
            }
        });
        mnuNewEnderecos.add(mnuNewEnderecoTipo);
        mnuNewEnderecos.add(jSeparator2NewEnderecos);

        mnuNewBairro.setText("Bairro");
        mnuNewBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewBairroActionPerformed(evt);
            }
        });
        mnuNewEnderecos.add(mnuNewBairro);

        mnuNewCidade.setText("Cidades");
        mnuNewCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewCidadeActionPerformed(evt);
            }
        });
        mnuNewEnderecos.add(mnuNewCidade);

        mnuNewEstado.setText("Estado");
        mnuNewEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewEstadoActionPerformed(evt);
            }
        });
        mnuNewEnderecos.add(mnuNewEstado);

        mnuNewPais.setText("País");
        mnuNewPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewPaisActionPerformed(evt);
            }
        });
        mnuNewEnderecos.add(mnuNewPais);

        mnuNovo.add(mnuNewEnderecos);

        mnuNewTelefones.setText("Telefones");

        mnuNewTelefone.setText("Telefone");
        mnuNewTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewTelefoneActionPerformed(evt);
            }
        });
        mnuNewTelefones.add(mnuNewTelefone);
        mnuNewTelefones.add(jSeparator1NewTelefones);

        mnuNewTelefoneTipo.setText("Telefone - Tipo");
        mnuNewTelefoneTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewTelefoneTipoActionPerformed(evt);
            }
        });
        mnuNewTelefones.add(mnuNewTelefoneTipo);

        mnuNovo.add(mnuNewTelefones);
        mnuNovo.add(jSeparator4New);

        mnuNewEspecializacao.setText("Especialização");
        mnuNewEspecializacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewEspecializacaoActionPerformed(evt);
            }
        });
        mnuNovo.add(mnuNewEspecializacao);

        mnuNewFuncao.setText("Função");
        mnuNewFuncao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewFuncaoActionPerformed(evt);
            }
        });
        mnuNovo.add(mnuNewFuncao);

        mnuNewPlanosSaude.setText("Planos de Saúde");

        mnuNewPlanoSaude.setText("Plano de Saúde");
        mnuNewPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewPlanoSaudeActionPerformed(evt);
            }
        });
        mnuNewPlanosSaude.add(mnuNewPlanoSaude);
        mnuNewPlanosSaude.add(jSeparator1NewPlanosSaude);

        mnuNewTipoPlanoSaude.setText("Tipo de Plano de Saúde");
        mnuNewTipoPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewTipoPlanoSaudeActionPerformed(evt);
            }
        });
        mnuNewPlanosSaude.add(mnuNewTipoPlanoSaude);

        mnuNovo.add(mnuNewPlanosSaude);

        mnuNewProntuario.setText("Prontuário");
        mnuNewProntuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewProntuarioActionPerformed(evt);
            }
        });
        mnuNovo.add(mnuNewProntuario);

        mnuSAMHO.add(mnuNovo);

        mnuConsultar.setText("Consultar Cadastros");

        mnuConsPessoas.setText("Pessoas");

        mnuConsPessoasFisicas.setText("Pessoas Físicas");
        mnuConsPessoasFisicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsPessoasFisicasActionPerformed(evt);
            }
        });
        mnuConsPessoas.add(mnuConsPessoasFisicas);

        mnuConsPessoasJuridicas.setText("Pessoas Jurídicas");
        mnuConsPessoasJuridicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsPessoasJuridicasActionPerformed(evt);
            }
        });
        mnuConsPessoas.add(mnuConsPessoasJuridicas);
        mnuConsPessoas.add(jSeparator1ConsPessoas);

        mnuConsSituacoesPessoas.setText("Situações de Pessoas");
        mnuConsSituacoesPessoas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsSituacoesPessoasActionPerformed(evt);
            }
        });
        mnuConsPessoas.add(mnuConsSituacoesPessoas);

        mnuConsEspecializacoesPessoas.setText("Pessoas - Especializações");
        mnuConsEspecializacoesPessoas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsEspecializacoesPessoasActionPerformed(evt);
            }
        });
        mnuConsPessoas.add(mnuConsEspecializacoesPessoas);

        mnuConsultar.add(mnuConsPessoas);
        mnuConsultar.add(jSeparator1Cons);

        mnuConsClientes.setText("Clientes");

        mnuConsCliente.setText("Clientes");
        mnuConsCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsClienteActionPerformed(evt);
            }
        });
        mnuConsClientes.add(mnuConsCliente);
        mnuConsClientes.add(jSeparator1ConsClientes);

        mnuConsTiposPlanosSaudeCliente.setText("Planos de Saúde dos Clientes");
        mnuConsTiposPlanosSaudeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsTiposPlanosSaudeClienteActionPerformed(evt);
            }
        });
        mnuConsClientes.add(mnuConsTiposPlanosSaudeCliente);

        mnuConsSituacoesClientes.setText("Situações de Clientes");
        mnuConsSituacoesClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsSituacoesClientesActionPerformed(evt);
            }
        });
        mnuConsClientes.add(mnuConsSituacoesClientes);

        mnuConsultar.add(mnuConsClientes);

        mnuConsFuncionaruios.setText("Funcionários");

        mnuConsFuncionario.setText("Funcionários");
        mnuConsFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsFuncionarioActionPerformed(evt);
            }
        });
        mnuConsFuncionaruios.add(mnuConsFuncionario);
        mnuConsFuncionaruios.add(jSeparator1ConsFuncionarios);

        mnuConsSituacoesFuncionarios.setText("Situações de Funcionários");
        mnuConsSituacoesFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsSituacoesFuncionariosActionPerformed(evt);
            }
        });
        mnuConsFuncionaruios.add(mnuConsSituacoesFuncionarios);

        mnuConsultar.add(mnuConsFuncionaruios);

        mnuConsProfissionais.setText("Profissionais");

        mnuConsProfissional.setText("Profissionais");
        mnuConsProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsProfissionalActionPerformed(evt);
            }
        });
        mnuConsProfissionais.add(mnuConsProfissional);
        mnuConsProfissionais.add(jSeparator1ConsProfissionais);

        mnuConsProfissionaisTurnos.setText("Turnos dos Profissionais");
        mnuConsProfissionaisTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsProfissionaisTurnosActionPerformed(evt);
            }
        });
        mnuConsProfissionais.add(mnuConsProfissionaisTurnos);

        mnuConsProfissionaisPlanosSaude.setText("Profissionais - Planos de Saúde");
        mnuConsProfissionaisPlanosSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsProfissionaisPlanosSaudeActionPerformed(evt);
            }
        });
        mnuConsProfissionais.add(mnuConsProfissionaisPlanosSaude);

        mnuConsultar.add(mnuConsProfissionais);
        mnuConsultar.add(jSeparator2Cons);

        mnuConsUsuarios.setText("Usuários");

        mnuConsUsuario.setText("Usuários");
        mnuConsUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsUsuarioActionPerformed(evt);
            }
        });
        mnuConsUsuarios.add(mnuConsUsuario);
        mnuConsUsuarios.add(jSeparator1ConsUsuarios);

        mnuConsUsuariosAcoes.setText("Ações dos Usuários");
        mnuConsUsuariosAcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsUsuariosAcoesActionPerformed(evt);
            }
        });
        mnuConsUsuarios.add(mnuConsUsuariosAcoes);

        mnuConsAcoes.setText("Ações");
        mnuConsAcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsAcoesActionPerformed(evt);
            }
        });
        mnuConsUsuarios.add(mnuConsAcoes);

        mnuConsultar.add(mnuConsUsuarios);
        mnuConsultar.add(jSeparator3Cons);

        mnuConsEnderecos.setText("Endereços");

        mnuConsEndereco.setText("Endereços");
        mnuConsEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsEnderecoActionPerformed(evt);
            }
        });
        mnuConsEnderecos.add(mnuConsEndereco);
        mnuConsEnderecos.add(jSeparator1ConsEnderecos);

        mnuConsEnderecosTipos.setText("Endereços - Tipos");
        mnuConsEnderecosTipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsEnderecosTiposActionPerformed(evt);
            }
        });
        mnuConsEnderecos.add(mnuConsEnderecosTipos);
        mnuConsEnderecos.add(jSeparator2ConsEnderecos);

        mnuConsBairros.setText("Bairro");
        mnuConsBairros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsBairrosActionPerformed(evt);
            }
        });
        mnuConsEnderecos.add(mnuConsBairros);

        mnuConsCidades.setText("Cidades");
        mnuConsCidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsCidadesActionPerformed(evt);
            }
        });
        mnuConsEnderecos.add(mnuConsCidades);

        mnuConsEstados.setText("Estados");
        mnuConsEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsEstadosActionPerformed(evt);
            }
        });
        mnuConsEnderecos.add(mnuConsEstados);

        mnuConsPaises.setText("Paises");
        mnuConsPaises.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsPaisesActionPerformed(evt);
            }
        });
        mnuConsEnderecos.add(mnuConsPaises);

        mnuConsultar.add(mnuConsEnderecos);

        mnuConsTelefones.setText("Telefones");

        mnuConsTelefone.setText("Telefones");
        mnuConsTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsTelefoneActionPerformed(evt);
            }
        });
        mnuConsTelefones.add(mnuConsTelefone);
        mnuConsTelefones.add(jSeparator1ConsTelefones);

        mnuConsTelefonesTipos.setText("Telefones - Tipos");
        mnuConsTelefonesTipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsTelefonesTiposActionPerformed(evt);
            }
        });
        mnuConsTelefones.add(mnuConsTelefonesTipos);

        mnuConsultar.add(mnuConsTelefones);
        mnuConsultar.add(jSeparator4Cons);

        mnuConsEspecializacoes.setText("Especializações");
        mnuConsEspecializacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsEspecializacoesActionPerformed(evt);
            }
        });
        mnuConsultar.add(mnuConsEspecializacoes);

        mnuConsFuncoes.setText("Funções");
        mnuConsFuncoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsFuncoesActionPerformed(evt);
            }
        });
        mnuConsultar.add(mnuConsFuncoes);

        mnuConsPlanosSaude.setText("Planos de Saúde");

        mnuConsPlanoSaude.setText("Planos de Saúde");
        mnuConsPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsPlanoSaudeActionPerformed(evt);
            }
        });
        mnuConsPlanosSaude.add(mnuConsPlanoSaude);
        mnuConsPlanosSaude.add(jSeparator1ConsPlanosSaude);

        mnuConsTiposPalanosSaude.setText("Tipos de Planos de Saúde");
        mnuConsTiposPalanosSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsTiposPalanosSaudeActionPerformed(evt);
            }
        });
        mnuConsPlanosSaude.add(mnuConsTiposPalanosSaude);

        mnuConsultar.add(mnuConsPlanosSaude);

        mnuConsProntuarios.setText("Prontuários");
        mnuConsProntuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsProntuariosActionPerformed(evt);
            }
        });
        mnuConsultar.add(mnuConsProntuarios);

        mnuSAMHO.add(mnuConsultar);
        mnuSAMHO.add(jSeparator1Arquivo);

        mnuPesquisar.setText("Pesquisar...");

        mnuGlossarioDoencas.setText("Glossário de Doenças");
        mnuGlossarioDoencas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGlossarioDoencasActionPerformed(evt);
            }
        });
        mnuPesquisar.add(mnuGlossarioDoencas);

        mnuMedicamentos.setText("Medicamentos");
        mnuMedicamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMedicamentosActionPerformed(evt);
            }
        });
        mnuPesquisar.add(mnuMedicamentos);

        mnuSintomasApresentados.setText("Sintomas Apresentados");
        mnuSintomasApresentados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSintomasApresentadosActionPerformed(evt);
            }
        });
        mnuPesquisar.add(mnuSintomasApresentados);

        mnuAgendas.setText("Agendas");

        mnuConsAgendas.setText("Agendas");
        mnuConsAgendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsAgendasActionPerformed(evt);
            }
        });
        mnuAgendas.add(mnuConsAgendas);
        mnuAgendas.add(jSeparator1Agendas);

        mnuConsMotivosAgendas.setText("Motivos Agendas");
        mnuConsMotivosAgendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConsMotivosAgendasActionPerformed(evt);
            }
        });
        mnuAgendas.add(mnuConsMotivosAgendas);

        mnuPesquisar.add(mnuAgendas);

        mnuSAMHO.add(mnuPesquisar);
        mnuSAMHO.add(jSeparator2Arquivo);

        mnuSair.setText("Sair");
        mnuSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSairActionPerformed(evt);
            }
        });
        mnuSAMHO.add(mnuSair);

        mnuBar.add(mnuSAMHO);

        mnuEditar.setText("Editar");

        mnuEditPessoas.setText("Pessoas");

        mnuEditPessoaFisica.setText("Pessoa Física");
        mnuEditPessoaFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditPessoaFisicaActionPerformed(evt);
            }
        });
        mnuEditPessoas.add(mnuEditPessoaFisica);

        mnuEditPessoaJuridica.setText("Pessoa Jurídica");
        mnuEditPessoaJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditPessoaJuridicaActionPerformed(evt);
            }
        });
        mnuEditPessoas.add(mnuEditPessoaJuridica);
        mnuEditPessoas.add(jSeparator1EditPessoas);

        mnuEditSituacaoPessoa.setText("Situação de Pessoas");
        mnuEditSituacaoPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditSituacaoPessoaActionPerformed(evt);
            }
        });
        mnuEditPessoas.add(mnuEditSituacaoPessoa);

        mnuEditEspecializacaoPessoa.setText("Pessoa - Especialização");
        mnuEditEspecializacaoPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditEspecializacaoPessoaActionPerformed(evt);
            }
        });
        mnuEditPessoas.add(mnuEditEspecializacaoPessoa);

        mnuEditar.add(mnuEditPessoas);
        mnuEditar.add(jSeparator1Edit);

        mnuEditClientes.setText("Clientes");

        mnuEditCliente.setText("Cliente");
        mnuEditCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditClienteActionPerformed(evt);
            }
        });
        mnuEditClientes.add(mnuEditCliente);
        mnuEditClientes.add(jSeparator1EditClientes);

        mnuEditTipoPlanoSaudeCliente.setText("Plano de Saúde do Cliente");
        mnuEditTipoPlanoSaudeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditTipoPlanoSaudeClienteActionPerformed(evt);
            }
        });
        mnuEditClientes.add(mnuEditTipoPlanoSaudeCliente);

        mnuEditSituacaoCliente.setText("Situação de Clientes");
        mnuEditSituacaoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditSituacaoClienteActionPerformed(evt);
            }
        });
        mnuEditClientes.add(mnuEditSituacaoCliente);

        mnuEditar.add(mnuEditClientes);

        mnuEditFuncionarios.setText("Funcionários");

        mnuEditFuncionario.setText("Funcionário");
        mnuEditFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditFuncionarioActionPerformed(evt);
            }
        });
        mnuEditFuncionarios.add(mnuEditFuncionario);
        mnuEditFuncionarios.add(jSeparator1EditFuncionarios);

        mnuEditSituacaoFuncionarios.setText("Situação de Funcionários");
        mnuEditSituacaoFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditSituacaoFuncionariosActionPerformed(evt);
            }
        });
        mnuEditFuncionarios.add(mnuEditSituacaoFuncionarios);

        mnuEditar.add(mnuEditFuncionarios);

        mnuEditProfissionais.setText("Profissionais");

        mnuEditProfissional.setText("Profissional");
        mnuEditProfissional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditProfissionalActionPerformed(evt);
            }
        });
        mnuEditProfissionais.add(mnuEditProfissional);
        mnuEditProfissionais.add(jSeparator1EditProfissionais);

        mnuEditProfissionalTurnos.setText("Profissional - Turnos");
        mnuEditProfissionalTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditProfissionalTurnosActionPerformed(evt);
            }
        });
        mnuEditProfissionais.add(mnuEditProfissionalTurnos);

        mnuEditProfissionalPlanoSaude.setText("Profissional - Planos de Saúde");
        mnuEditProfissionalPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditProfissionalPlanoSaudeActionPerformed(evt);
            }
        });
        mnuEditProfissionais.add(mnuEditProfissionalPlanoSaude);

        mnuEditar.add(mnuEditProfissionais);
        mnuEditar.add(jSeparator2Edit);

        mnuEditUsuarios.setText("Usuários");

        mnuEditUsuario.setText("Usuário");
        mnuEditUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditUsuarioActionPerformed(evt);
            }
        });
        mnuEditUsuarios.add(mnuEditUsuario);
        mnuEditUsuarios.add(jSeparator1EditUsuarios);

        mnuEditUsuarioAcoes.setText("Ações do Usuário");
        mnuEditUsuarioAcoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditUsuarioAcoesActionPerformed(evt);
            }
        });
        mnuEditUsuarios.add(mnuEditUsuarioAcoes);

        mnuEditAcao.setText("Ação");
        mnuEditAcao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditAcaoActionPerformed(evt);
            }
        });
        mnuEditUsuarios.add(mnuEditAcao);

        mnuEditar.add(mnuEditUsuarios);
        mnuEditar.add(jSeparator3Edit);

        mnuEditEnderecos.setText("Endereços");

        mnuEditEndereco.setText("Endereco");
        mnuEditEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditEnderecoActionPerformed(evt);
            }
        });
        mnuEditEnderecos.add(mnuEditEndereco);
        mnuEditEnderecos.add(jSeparator1EditEnderecos);

        mnuEditEnderecoTipo.setText("Endereço - Tipos");
        mnuEditEnderecoTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditEnderecoTipoActionPerformed(evt);
            }
        });
        mnuEditEnderecos.add(mnuEditEnderecoTipo);
        mnuEditEnderecos.add(jSeparator2EditEnderecos);

        mnuEditBairro.setText("Bairro");
        mnuEditBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditBairroActionPerformed(evt);
            }
        });
        mnuEditEnderecos.add(mnuEditBairro);

        mnuEditCidade.setText("Cidade");
        mnuEditCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditCidadeActionPerformed(evt);
            }
        });
        mnuEditEnderecos.add(mnuEditCidade);

        mnuEditEstado.setText("Estado");
        mnuEditEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditEstadoActionPerformed(evt);
            }
        });
        mnuEditEnderecos.add(mnuEditEstado);

        mnuEditPais.setText("País");
        mnuEditPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditPaisActionPerformed(evt);
            }
        });
        mnuEditEnderecos.add(mnuEditPais);

        mnuEditar.add(mnuEditEnderecos);

        mnuEditTelefones.setText("Telefones");

        mnuEditTelefone.setText("Telefone");
        mnuEditTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditTelefoneActionPerformed(evt);
            }
        });
        mnuEditTelefones.add(mnuEditTelefone);
        mnuEditTelefones.add(jSeparator1EditTelefones);

        mnuEditTelefoneTipo.setText("Telefone - Tipo");
        mnuEditTelefoneTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditTelefoneTipoActionPerformed(evt);
            }
        });
        mnuEditTelefones.add(mnuEditTelefoneTipo);

        mnuEditar.add(mnuEditTelefones);
        mnuEditar.add(jSeparator4Edit);

        mnuEditEspecializacao.setText("Especialização");
        mnuEditEspecializacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditEspecializacaoActionPerformed(evt);
            }
        });
        mnuEditar.add(mnuEditEspecializacao);

        mnuEditFuncao.setText("Função");
        mnuEditFuncao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditFuncaoActionPerformed(evt);
            }
        });
        mnuEditar.add(mnuEditFuncao);

        mnuEditPlanosSaude.setText("Planos de Saúde");

        mnuEditPlanoSaude.setText("Plano de Saúde");
        mnuEditPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditPlanoSaudeActionPerformed(evt);
            }
        });
        mnuEditPlanosSaude.add(mnuEditPlanoSaude);
        mnuEditPlanosSaude.add(jSeparator1EditPlanosSaude);

        mnuEditTipoPlanoSaude.setText("Tipo de Plano de Saúde");
        mnuEditTipoPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditTipoPlanoSaudeActionPerformed(evt);
            }
        });
        mnuEditPlanosSaude.add(mnuEditTipoPlanoSaude);

        mnuEditar.add(mnuEditPlanosSaude);

        mnuEditProntuario.setText("Prontuário");
        mnuEditProntuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditProntuarioActionPerformed(evt);
            }
        });
        mnuEditar.add(mnuEditProntuario);

        mnuBar.add(mnuEditar);

        mnuFerramentas.setText("Ferramentas");

        mnuEditAparencia.setText("Aparência");
        mnuEditAparencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditAparenciaActionPerformed(evt);
            }
        });
        mnuFerramentas.add(mnuEditAparencia);

        mnuEditBackup.setText("Backup");
        mnuEditBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuEditBackupActionPerformed(evt);
            }
        });
        mnuFerramentas.add(mnuEditBackup);

        mnuBar.add(mnuFerramentas);

        mnuRelatorios.setText("Relatórios");

        mnuRelPessoasFisicas.setText("Pessoas Físicas");
        mnuRelPessoasFisicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRelPessoasFisicasActionPerformed(evt);
            }
        });
        mnuRelatorios.add(mnuRelPessoasFisicas);

        mnuBar.add(mnuRelatorios);

        mnuAjuda.setText("Ajuda");

        mnuSobreSANHO.setText("Sobre SAMHO");
        mnuSobreSANHO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSobreSANHOActionPerformed(evt);
            }
        });
        mnuAjuda.add(mnuSobreSANHO);

        mnuBar.add(mnuAjuda);

        setJMenuBar(mnuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(descPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(descPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuSairActionPerformed

    private void mnuEditAparenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditAparenciaActionPerformed
        try {
            IFrmManAparencia iFrmManAparencia
                    = new IFrmManAparencia(lblImagemFundo, this);

            descPane.remove(iFrmManAparencia);
            descPane.add(iFrmManAparencia);

            Util.centralizar(this, iFrmManAparencia);

            iFrmManAparencia.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Aparência não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditAparenciaActionPerformed

    private void mnuSobreSANHOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSobreSANHOActionPerformed
        try {
            IFrmAjuda jFrmAjuda = new IFrmAjuda();

            descPane.remove(jFrmAjuda);
            descPane.add(jFrmAjuda);

            Util.centralizar(this, jFrmAjuda);

            jFrmAjuda.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Sobre não está acessível.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuSobreSANHOActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
       setAparenciaPage();
       autenticarUsuário();
    }//GEN-LAST:event_formComponentShown

    private void mnuEditBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditBackupActionPerformed
        try {
            IFrmManBackup iFrmManBackup = new IFrmManBackup();

            descPane.remove(iFrmManBackup);
            descPane.add(iFrmManBackup);

            Util.centralizar(this, iFrmManBackup);

            iFrmManBackup.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao salvar arquivo de configuração de backup: " + ex);
        }
    }//GEN-LAST:event_mnuEditBackupActionPerformed

    private void mnuNewPessoaFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewPessoaFisicaActionPerformed
        try {
            Pessoas pessoa = new Pessoas(Pessoas.TIPO_FISICA);
            IFrmManPessoa iFrmManPessoa = new IFrmManPessoa(pessoa);
            iFrmManPessoa.carregarObjetoIFrmManutencao(pessoa);

            descPane.remove(iFrmManPessoa);
            descPane.add(iFrmManPessoa);

            Util.centralizar(this, iFrmManPessoa);

            iFrmManPessoa.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Pessoa não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewPessoaFisicaActionPerformed

    private void mnuNewPessoaJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewPessoaJuridicaActionPerformed
        try {
            Pessoas pessoa = new Pessoas(Pessoas.TIPO_JURIDICA);
            IFrmManPessoa iFrmManPessoa = new IFrmManPessoa(pessoa);
            iFrmManPessoa.carregarObjetoIFrmManutencao(pessoa);

            descPane.remove(iFrmManPessoa);
            descPane.add(iFrmManPessoa);

            Util.centralizar(this, iFrmManPessoa);

            iFrmManPessoa.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Pessoa não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewPessoaJuridicaActionPerformed

    private void mnuConsPessoasFisicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsPessoasFisicasActionPerformed
        try {
            IFrmPesqPessoas iFrmPesqPessoas = new IFrmPesqPessoas(
                    new IFrmManPessoa(new Pessoas(Pessoas.TIPO_FISICA)));

            descPane.remove(iFrmPesqPessoas);
            descPane.add(iFrmPesqPessoas);

            Util.centralizar(this, iFrmPesqPessoas);
            Util.maximizar(iFrmPesqPessoas);

            iFrmPesqPessoas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Pessoas Físicas não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsPessoasFisicasActionPerformed

    private void mnuConsPessoasJuridicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsPessoasJuridicasActionPerformed
        try {
            IFrmPesqPessoas iFrmPesqPessoas = new IFrmPesqPessoas(
                    new IFrmManPessoa(new Pessoas(Pessoas.TIPO_JURIDICA)));

            descPane.remove(iFrmPesqPessoas);
            descPane.add(iFrmPesqPessoas);

            Util.centralizar(this, iFrmPesqPessoas);
            Util.maximizar(iFrmPesqPessoas);

            iFrmPesqPessoas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Pessoas Jurídicas não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsPessoasJuridicasActionPerformed

    private void mnuEditPessoaFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditPessoaFisicaActionPerformed
        try {
            IFrmPesqPessoas iFrmPesqPessoas = new IFrmPesqPessoas(
                    new IFrmManPessoa(new Pessoas(Pessoas.TIPO_FISICA)));

            descPane.remove(iFrmPesqPessoas);
            descPane.add(iFrmPesqPessoas);

            Util.centralizar(this, iFrmPesqPessoas);
            Util.maximizar(iFrmPesqPessoas);

            iFrmPesqPessoas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Pessoas Físicas não podem ser alteradas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditPessoaFisicaActionPerformed

    private void mnuEditPessoaJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditPessoaJuridicaActionPerformed
        try {
            IFrmPesqPessoas iFrmPesqPessoas = new IFrmPesqPessoas(
                    new IFrmManPessoa(new Pessoas(Pessoas.TIPO_JURIDICA)));

            descPane.remove(iFrmPesqPessoas);
            descPane.add(iFrmPesqPessoas);

            Util.centralizar(this, iFrmPesqPessoas);
            Util.maximizar(iFrmPesqPessoas);

            iFrmPesqPessoas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Pessoas Jurídicas não podem ser alteradas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditPessoaJuridicaActionPerformed

    private void mnuNewSituacaoPessoasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewSituacaoPessoasActionPerformed
        try {
            IFrmManSituacaoPessoas iFrmManSituacaoPessoas =
                    new IFrmManSituacaoPessoas(new SituacoesPessoas());

            descPane.remove(iFrmManSituacaoPessoas);

            Util.centralizar(this, iFrmManSituacaoPessoas);

            iFrmManSituacaoPessoas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situação das Pessoas não pode ser criadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewSituacaoPessoasActionPerformed

    private void mnuConsSituacoesPessoasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsSituacoesPessoasActionPerformed
        try {
            IFrmPesqSituacoesPessoas iFrmPesqSituacoesPessoas = 
                    new IFrmPesqSituacoesPessoas(new IFrmManSituacaoPessoas(
                            new SituacoesPessoas()));

            descPane.remove(iFrmPesqSituacoesPessoas);
            descPane.add(iFrmPesqSituacoesPessoas);

            Util.centralizar(this, iFrmPesqSituacoesPessoas);
            Util.maximizar(iFrmPesqSituacoesPessoas);

            iFrmPesqSituacoesPessoas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situações das Pessoas não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsSituacoesPessoasActionPerformed

    private void mnuEditSituacaoPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditSituacaoPessoaActionPerformed
        try {
            IFrmPesqSituacoesPessoas iFrmPesqSituacoesPessoas = 
                    new IFrmPesqSituacoesPessoas(new IFrmManSituacaoPessoas(
                            new SituacoesPessoas()));

            descPane.remove(iFrmPesqSituacoesPessoas);
            descPane.add(iFrmPesqSituacoesPessoas);

            Util.centralizar(this, iFrmPesqSituacoesPessoas);
            Util.maximizar(iFrmPesqSituacoesPessoas);

            iFrmPesqSituacoesPessoas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situação das Pessoas não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditSituacaoPessoaActionPerformed

    private void mnuNewFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewFuncionarioActionPerformed
       try {
            IFrmManFuncionario iFrmManFuncionario = 
                    new IFrmManFuncionario(new Funcionarios());

            descPane.remove(iFrmManFuncionario);
            descPane.add(iFrmManFuncionario);

            Util.centralizar(this, iFrmManFuncionario);

            iFrmManFuncionario.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Funcionário não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewFuncionarioActionPerformed

    private void mnuConsFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsFuncionarioActionPerformed
       try {
            IFrmPesqFuncionarios iFrmPesqFuncionarios = 
                    new IFrmPesqFuncionarios(new IFrmManFuncionario(
                            new Funcionarios()));

            descPane.remove(iFrmPesqFuncionarios);
            descPane.add(iFrmPesqFuncionarios);

            Util.centralizar(this, iFrmPesqFuncionarios);
            Util.maximizar(iFrmPesqFuncionarios);

            iFrmPesqFuncionarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Funcionários não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsFuncionarioActionPerformed

    private void mnuEditFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditFuncionarioActionPerformed
       try {
            IFrmPesqFuncionarios iFrmPesqFuncionarios = 
                    new IFrmPesqFuncionarios(new IFrmManFuncionario(
                            new Funcionarios()));

            descPane.remove(iFrmPesqFuncionarios);
            descPane.add(iFrmPesqFuncionarios);

            Util.centralizar(this, iFrmPesqFuncionarios);
            Util.maximizar(iFrmPesqFuncionarios);

            iFrmPesqFuncionarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Funcionário não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditFuncionarioActionPerformed

    private void mnuNewSituacaoFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewSituacaoFuncionariosActionPerformed
        try {
            IFrmManSituacaoFuncionarios iFrmManSituacaoFuncionarios = 
                    new IFrmManSituacaoFuncionarios(
                            new SituacoesFuncionarios());

            descPane.remove(iFrmManSituacaoFuncionarios);
            descPane.add(iFrmManSituacaoFuncionarios);

            Util.centralizar(this, iFrmManSituacaoFuncionarios);

            iFrmManSituacaoFuncionarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situação dos Funcionários não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewSituacaoFuncionariosActionPerformed

    private void mnuConsSituacoesFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsSituacoesFuncionariosActionPerformed
        try {
            IFrmPesqSituacoesFuncionarios iFrmPesqSituacoesFuncionarios = 
                    new IFrmPesqSituacoesFuncionarios(
                            new IFrmManSituacaoFuncionarios(
                                    new SituacoesFuncionarios()));

            descPane.remove(iFrmPesqSituacoesFuncionarios);
            descPane.add(iFrmPesqSituacoesFuncionarios);

            Util.centralizar(this, iFrmPesqSituacoesFuncionarios);
            Util.maximizar(iFrmPesqSituacoesFuncionarios);

            iFrmPesqSituacoesFuncionarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situações dos Funcionários não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsSituacoesFuncionariosActionPerformed

    private void mnuEditSituacaoFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditSituacaoFuncionariosActionPerformed
        try {
            IFrmPesqSituacoesFuncionarios iFrmPesqSituacoesFuncionarios = 
                    new IFrmPesqSituacoesFuncionarios(
                            new IFrmManSituacaoFuncionarios(
                                    new SituacoesFuncionarios()));

            descPane.remove(iFrmPesqSituacoesFuncionarios);
            descPane.add(iFrmPesqSituacoesFuncionarios);

            Util.centralizar(this, iFrmPesqSituacoesFuncionarios);
            Util.maximizar(iFrmPesqSituacoesFuncionarios);

            iFrmPesqSituacoesFuncionarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situação dos Funcionários não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditSituacaoFuncionariosActionPerformed

    private void mnuNewProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewProfissionalActionPerformed
        try {
            IFrmManProfissional iFrmManProfissional = 
                    new IFrmManProfissional(new Profissionais());

            descPane.remove(iFrmManProfissional);
            descPane.add(iFrmManProfissional);

            Util.centralizar(this, iFrmManProfissional);

            iFrmManProfissional.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Profissional não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewProfissionalActionPerformed

    private void mnuEditProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditProfissionalActionPerformed
        try {
            IFrmPesqProfissionais iFrmPesqProfissionais = 
                    new IFrmPesqProfissionais(new IFrmManProfissional(
                            new Profissionais()));

            descPane.remove(iFrmPesqProfissionais);
            descPane.add(iFrmPesqProfissionais);

            Util.centralizar(this, iFrmPesqProfissionais);
            Util.maximizar(iFrmPesqProfissionais);

            iFrmPesqProfissionais.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Profissional não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditProfissionalActionPerformed

    private void mnuConsProfissionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsProfissionalActionPerformed
        try {
            IFrmPesqProfissionais iFrmPesqProfissionais = 
                    new IFrmPesqProfissionais(
                            new IFrmManProfissional(
                                    new Profissionais()));

            descPane.remove(iFrmPesqProfissionais);
            descPane.add(iFrmPesqProfissionais);

            Util.centralizar(this, iFrmPesqProfissionais);
            Util.maximizar(iFrmPesqProfissionais);

            iFrmPesqProfissionais.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Profissionais não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsProfissionalActionPerformed

    private void mnuNewProfissionalTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewProfissionalTurnosActionPerformed
       try {
            IFrmManProfissionalTurnos iFrmManProfissionalTurnos =
                    new IFrmManProfissionalTurnos(new ProfissionaisTurnos());

            descPane.remove(iFrmManProfissionalTurnos);
            descPane.add(iFrmManProfissionalTurnos);

            Util.centralizar(this, iFrmManProfissionalTurnos);

            iFrmManProfissionalTurnos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Turnos do Profissional não podem ser criados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewProfissionalTurnosActionPerformed

    private void mnuConsProfissionaisTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsProfissionaisTurnosActionPerformed
       try {
            IFrmPesqProfissionaisTurnos iFrmPesqProfissionaisTurnos
                    = new IFrmPesqProfissionaisTurnos(
                            new IFrmManProfissionalTurnos(
                                    new ProfissionaisTurnos()));

            descPane.remove(iFrmPesqProfissionaisTurnos);
            descPane.add(iFrmPesqProfissionaisTurnos);

            Util.centralizar(this, iFrmPesqProfissionaisTurnos);
            Util.maximizar(iFrmPesqProfissionaisTurnos);

            iFrmPesqProfissionaisTurnos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Turnos dos Profissionais não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsProfissionaisTurnosActionPerformed

    private void mnuEditProfissionalTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditProfissionalTurnosActionPerformed
       try {
            IFrmPesqProfissionaisTurnos iFrmPesqProfissionaisTurnos
                    = new IFrmPesqProfissionaisTurnos(
                            new IFrmManProfissionalTurnos(
                                    new ProfissionaisTurnos()));

            descPane.remove(iFrmPesqProfissionaisTurnos);
            descPane.add(iFrmPesqProfissionaisTurnos);

            Util.centralizar(this, iFrmPesqProfissionaisTurnos);
            Util.maximizar(iFrmPesqProfissionaisTurnos);

            iFrmPesqProfissionaisTurnos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Turnos do Profissional não podem ser alterados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditProfissionalTurnosActionPerformed

    private void mnuNewEspecializacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewEspecializacaoActionPerformed
       try {
            IFrmManEspecializacao iFrmManEspecializacao =
                    new IFrmManEspecializacao(new Especializacoes());

            descPane.remove(iFrmManEspecializacao);
            descPane.add(iFrmManEspecializacao);

            Util.centralizar(this, iFrmManEspecializacao);

            iFrmManEspecializacao.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Especialização não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewEspecializacaoActionPerformed

    private void mnuConsEspecializacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsEspecializacoesActionPerformed
       try {
            IFrmPesqEspecializacoes iFrmPesqEspecializacoes
                    = new IFrmPesqEspecializacoes(
                            new IFrmManEspecializacao(new Especializacoes()));

            descPane.remove(iFrmPesqEspecializacoes);
            descPane.add(iFrmPesqEspecializacoes);

            Util.centralizar(this, iFrmPesqEspecializacoes);
            Util.maximizar(iFrmPesqEspecializacoes);

            iFrmPesqEspecializacoes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Especializações não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsEspecializacoesActionPerformed

    private void mnuEditEspecializacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditEspecializacaoActionPerformed
       try {
            IFrmPesqEspecializacoes iFrmPesqEspecializacoes
                    = new IFrmPesqEspecializacoes(
                            new IFrmManEspecializacao(new Especializacoes()));

            descPane.remove(iFrmPesqEspecializacoes);
            descPane.add(iFrmPesqEspecializacoes);

            Util.centralizar(this, iFrmPesqEspecializacoes);
            Util.maximizar(iFrmPesqEspecializacoes);

            iFrmPesqEspecializacoes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Especialização não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditEspecializacaoActionPerformed

    private void mnuNewUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewUsuarioActionPerformed
       try {
            IFrmManUsuario iFrmManUsuario = new IFrmManUsuario(new Usuarios());

            descPane.remove(iFrmManUsuario);
            descPane.add(iFrmManUsuario);

            Util.centralizar(this, iFrmManUsuario);

            iFrmManUsuario.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Usuário não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewUsuarioActionPerformed

    private void mnuConsUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsUsuarioActionPerformed
       try {
            IFrmPesqUsuarios iFrmPesqUsuarios = new IFrmPesqUsuarios(
                    new IFrmManUsuario(new Usuarios()));

            descPane.remove(iFrmPesqUsuarios);
            descPane.add(iFrmPesqUsuarios);

            Util.centralizar(this, iFrmPesqUsuarios);
            Util.maximizar(iFrmPesqUsuarios);

            iFrmPesqUsuarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Usuários não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsUsuarioActionPerformed

    private void mnuEditUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditUsuarioActionPerformed
       try {
            IFrmPesqUsuarios iFrmPesqUsuarios = new IFrmPesqUsuarios(
                    new IFrmManUsuario(new Usuarios()));

            descPane.remove(iFrmPesqUsuarios);
            descPane.add(iFrmPesqUsuarios);

            Util.centralizar(this, iFrmPesqUsuarios);
            Util.maximizar(iFrmPesqUsuarios);

            iFrmPesqUsuarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Usuário não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditUsuarioActionPerformed

    private void mnuNewUsuarioAcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewUsuarioAcoesActionPerformed
       try {
            IFrmManAcoesUsuario iFrmManAcoesUsuario = 
                    new IFrmManAcoesUsuario(new AcoesUsuarios());

            descPane.remove(iFrmManAcoesUsuario);
            descPane.add(iFrmManAcoesUsuario);

            Util.centralizar(this, iFrmManAcoesUsuario);

            iFrmManAcoesUsuario.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Ações do Usuário não podem ser criadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewUsuarioAcoesActionPerformed

    private void mnuConsUsuariosAcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsUsuariosAcoesActionPerformed
       try {
            IFrmPesqAcoesUsuarios iFrmPesqAcoesUsuarios
                    = new IFrmPesqAcoesUsuarios(
                            new IFrmManAcoesUsuario(new AcoesUsuarios()));

            descPane.remove(iFrmPesqAcoesUsuarios);
            descPane.add(iFrmPesqAcoesUsuarios);

            Util.centralizar(this, iFrmPesqAcoesUsuarios);
            Util.maximizar(iFrmPesqAcoesUsuarios);

            iFrmPesqAcoesUsuarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Ações dos Usuários não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsUsuariosAcoesActionPerformed

    private void mnuEditUsuarioAcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditUsuarioAcoesActionPerformed
       try {
            IFrmPesqAcoesUsuarios iFrmPesqAcoesUsuarios
                    = new IFrmPesqAcoesUsuarios(
                            new IFrmManAcoesUsuario(new AcoesUsuarios()));

            descPane.remove(iFrmPesqAcoesUsuarios);
            descPane.add(iFrmPesqAcoesUsuarios);

            Util.centralizar(this, iFrmPesqAcoesUsuarios);
            Util.maximizar(iFrmPesqAcoesUsuarios);

            iFrmPesqAcoesUsuarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Ações do Usuário não podem ser alteradas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditUsuarioAcoesActionPerformed

    private void mnuNewAcaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewAcaoActionPerformed
       try {
            IFrmManAcao iFrmManAcao = new IFrmManAcao(new Acoes());

            descPane.remove(iFrmManAcao);
            descPane.add(iFrmManAcao);

            Util.centralizar(this, iFrmManAcao);

            iFrmManAcao.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Ação não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewAcaoActionPerformed

    private void mnuConsAcoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsAcoesActionPerformed
       try {
            IFrmPesqAcoes iFrmPesqAcoes = new IFrmPesqAcoes(new IFrmManAcao(
                    new Acoes()));

            descPane.remove(iFrmPesqAcoes);
            descPane.add(iFrmPesqAcoes);

            Util.centralizar(this, iFrmPesqAcoes);
            Util.maximizar(iFrmPesqAcoes);

            iFrmPesqAcoes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Ações não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsAcoesActionPerformed

    private void mnuEditAcaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditAcaoActionPerformed
       try {
            IFrmPesqAcoes iFrmPesqAcoes = new IFrmPesqAcoes(new IFrmManAcao(
                    new Acoes()));

            descPane.remove(iFrmPesqAcoes);
            descPane.add(iFrmPesqAcoes);

            Util.centralizar(this, iFrmPesqAcoes);
            Util.maximizar(iFrmPesqAcoes);

            iFrmPesqAcoes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Ação não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditAcaoActionPerformed

    private void mnuNewFuncaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewFuncaoActionPerformed
       try {
            IFrmManFuncao iFrmManFuncao = new IFrmManFuncao(new Funcoes());

            descPane.remove(iFrmManFuncao);
            descPane.add(iFrmManFuncao);

            Util.centralizar(this, iFrmManFuncao);

            iFrmManFuncao.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Função não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewFuncaoActionPerformed

    private void mnuConsFuncoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsFuncoesActionPerformed
       try {
            IFrmPesqFuncoes iFrmPesqFuncoes = new IFrmPesqFuncoes(
                    new IFrmManFuncao(new Funcoes()));

            descPane.remove(iFrmPesqFuncoes);
            descPane.add(iFrmPesqFuncoes);

            Util.centralizar(this, iFrmPesqFuncoes);
            Util.maximizar(iFrmPesqFuncoes);

            iFrmPesqFuncoes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Funções não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsFuncoesActionPerformed

    private void mnuEditFuncaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditFuncaoActionPerformed
       try {
            IFrmPesqFuncoes iFrmPesqFuncoes = new IFrmPesqFuncoes(
                    new IFrmManFuncao(new Funcoes()));

            descPane.remove(iFrmPesqFuncoes);
            descPane.add(iFrmPesqFuncoes);

            Util.centralizar(this, iFrmPesqFuncoes);
            Util.maximizar(iFrmPesqFuncoes);

            iFrmPesqFuncoes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Função não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditFuncaoActionPerformed

    private void mnuNewEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewEnderecoActionPerformed
        try {
            IFrmManEndereco iFrmManEndereco = new IFrmManEndereco(new Enderecos());

            descPane.remove(iFrmManEndereco);
            descPane.add(iFrmManEndereco);

            Util.centralizar(this, iFrmManEndereco);

            iFrmManEndereco.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Endereço não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewEnderecoActionPerformed

    private void mnuConsEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsEnderecoActionPerformed
        try {
            IFrmPesqEnderecos iFrmPesqEnderecos = new IFrmPesqEnderecos(
                    new IFrmManEndereco(new Enderecos()));

            descPane.remove(iFrmPesqEnderecos);
            descPane.add(iFrmPesqEnderecos);

            Util.centralizar(this, iFrmPesqEnderecos);
            Util.maximizar(iFrmPesqEnderecos);

            iFrmPesqEnderecos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Endereços não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsEnderecoActionPerformed

    private void mnuEditEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditEnderecoActionPerformed
        try {
            IFrmPesqEnderecos iFrmPesqEnderecos = new IFrmPesqEnderecos(
                    new IFrmManEndereco(new Enderecos()));

            descPane.remove(iFrmPesqEnderecos);
            descPane.add(iFrmPesqEnderecos);

            Util.centralizar(this, iFrmPesqEnderecos);
            Util.maximizar(iFrmPesqEnderecos);

            iFrmPesqEnderecos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Endereço não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditEnderecoActionPerformed

    private void mnuNewPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewPaisActionPerformed
        try {
            IFrmManPais iFrmManPais = new IFrmManPais(new Paises());

            descPane.remove(iFrmManPais);
            descPane.add(iFrmManPais);

            Util.centralizar(this, iFrmManPais);

            iFrmManPais.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "País não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewPaisActionPerformed

    private void mnuConsPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsPaisesActionPerformed
        try {
            IFrmPesqPaises iFrmPesqPaises = new IFrmPesqPaises(
                    new IFrmManPais(new Paises()));

            descPane.remove(iFrmPesqPaises);
            descPane.add(iFrmPesqPaises);

            Util.centralizar(this, iFrmPesqPaises);
            Util.maximizar(iFrmPesqPaises);

            iFrmPesqPaises.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Paises não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsPaisesActionPerformed

    private void mnuEditPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditPaisActionPerformed
        try {
            IFrmPesqPaises iFrmPesqPaises = new IFrmPesqPaises(
                    new IFrmManPais(new Paises()));

            descPane.remove(iFrmPesqPaises);
            descPane.add(iFrmPesqPaises);

            Util.centralizar(this, iFrmPesqPaises);
            Util.maximizar(iFrmPesqPaises);

            iFrmPesqPaises.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "País não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditPaisActionPerformed

    private void mnuNewEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewEstadoActionPerformed
        try {
            IFrmManEstado iFrmManEstado = new IFrmManEstado(new Estados());

            descPane.remove(iFrmManEstado);
            descPane.add(iFrmManEstado);

            Util.centralizar(this, iFrmManEstado);

            iFrmManEstado.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Estado não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewEstadoActionPerformed

    private void mnuConsEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsEstadosActionPerformed
        try {
            IFrmPesqEstados iFrmPesqEstados = new IFrmPesqEstados(
                    new IFrmManEstado(new Estados()));

            descPane.remove(iFrmPesqEstados);
            descPane.add(iFrmPesqEstados);

            Util.centralizar(this, iFrmPesqEstados);
            Util.maximizar(iFrmPesqEstados);

            iFrmPesqEstados.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Estados não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsEstadosActionPerformed

    private void mnuEditEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditEstadoActionPerformed
        try {
            IFrmPesqEstados iFrmPesqEstados = new IFrmPesqEstados(
                    new IFrmManEstado(new Estados()));

            descPane.remove(iFrmPesqEstados);
            descPane.add(iFrmPesqEstados);

            Util.centralizar(this, iFrmPesqEstados);
            Util.maximizar(iFrmPesqEstados);

            iFrmPesqEstados.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Estado não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditEstadoActionPerformed

    private void mnuNewBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewBairroActionPerformed
        try {
            IFrmManBairro iFrmManBairro = new IFrmManBairro(new Bairros());

            descPane.remove(iFrmManBairro);
            descPane.add(iFrmManBairro);

            Util.centralizar(this, iFrmManBairro);

            iFrmManBairro.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Bairro não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewBairroActionPerformed

    private void mnuConsBairrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsBairrosActionPerformed
        try {
            IFrmPesqBairros iFrmPesqBairros = new IFrmPesqBairros(
                    new IFrmManBairro(new Bairros()));

            descPane.remove(iFrmPesqBairros);
            descPane.add(iFrmPesqBairros);

            Util.centralizar(this, iFrmPesqBairros);
            Util.maximizar(iFrmPesqBairros);

            iFrmPesqBairros.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Bairros não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsBairrosActionPerformed

    private void mnuEditBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditBairroActionPerformed
        try {
            IFrmPesqBairros iFrmPesqBairros = new IFrmPesqBairros(
                    new IFrmManBairro(new Bairros()));

            descPane.remove(iFrmPesqBairros);
            descPane.add(iFrmPesqBairros);

            Util.centralizar(this, iFrmPesqBairros);
            Util.maximizar(iFrmPesqBairros);

            iFrmPesqBairros.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Bairro não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditBairroActionPerformed

    private void mnuNewCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewCidadeActionPerformed
        try {
            IFrmManCidade iFrmManCidade = new IFrmManCidade(new Cidades());

            descPane.remove(iFrmManCidade);
            descPane.add(iFrmManCidade);

            Util.centralizar(this, iFrmManCidade);

            iFrmManCidade.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Cidade não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewCidadeActionPerformed

    private void mnuConsCidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsCidadesActionPerformed
        try {
            IFrmPesqCidades iFrmPesqCidades = new IFrmPesqCidades(
                    new IFrmManCidade(new Cidades()));

            descPane.remove(iFrmPesqCidades);
            descPane.add(iFrmPesqCidades);

            Util.centralizar(this, iFrmPesqCidades);
            Util.maximizar(iFrmPesqCidades);

            iFrmPesqCidades.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Cidades não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsCidadesActionPerformed

    private void mnuEditCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditCidadeActionPerformed
        try {
            IFrmPesqCidades iFrmPesqCidades = new IFrmPesqCidades(
                    new IFrmManCidade(new Cidades()));

            descPane.remove(iFrmPesqCidades);
            descPane.add(iFrmPesqCidades);

            Util.centralizar(this, iFrmPesqCidades);
            Util.maximizar(iFrmPesqCidades);

            iFrmPesqCidades.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Cidade não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditCidadeActionPerformed

    private void mnuNewEnderecoTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewEnderecoTipoActionPerformed
        try {
            IFrmManTipoEndereco iFrmManTipoEndereco = 
                    new IFrmManTipoEndereco(new TiposEnderecos());

            descPane.remove(iFrmManTipoEndereco);
            descPane.add(iFrmManTipoEndereco);

            Util.centralizar(this, iFrmManTipoEndereco);

            iFrmManTipoEndereco.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipo de Endereços não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewEnderecoTipoActionPerformed

    private void mnuConsEnderecosTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsEnderecosTiposActionPerformed
        try {
            IFrmPesqTiposEnderecos iFrmPesqTiposEnderecos = new IFrmPesqTiposEnderecos(
                    new IFrmManTipoEndereco(new TiposEnderecos()));

            descPane.remove(iFrmPesqTiposEnderecos);
            descPane.add(iFrmPesqTiposEnderecos);

            Util.centralizar(this, iFrmPesqTiposEnderecos);
            Util.maximizar(iFrmPesqTiposEnderecos);

            iFrmPesqTiposEnderecos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipos de Endereços não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsEnderecosTiposActionPerformed

    private void mnuEditEnderecoTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditEnderecoTipoActionPerformed
        try {
            IFrmPesqTiposEnderecos iFrmPesqTiposEnderecos = new IFrmPesqTiposEnderecos(
                    new IFrmManTipoEndereco(new TiposEnderecos()));

            descPane.remove(iFrmPesqTiposEnderecos);
            descPane.add(iFrmPesqTiposEnderecos);

            Util.centralizar(this, iFrmPesqTiposEnderecos);
            Util.maximizar(iFrmPesqTiposEnderecos);

            iFrmPesqTiposEnderecos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipo de Endereços não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditEnderecoTipoActionPerformed

    private void mnuNewTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewTelefoneActionPerformed
        try {    
            IFrmManTelefone iFrmManTelefone = new IFrmManTelefone(new Telefones());

            descPane.remove(iFrmManTelefone);
            descPane.add(iFrmManTelefone);

            Util.centralizar(this, iFrmManTelefone);

            iFrmManTelefone.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Telefone não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewTelefoneActionPerformed

    private void mnuConsTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsTelefoneActionPerformed
        try {    
            IFrmPesqTelefones iFrmPesqTelefones = new IFrmPesqTelefones(
                    new IFrmManTelefone(new Telefones()));

            descPane.remove(iFrmPesqTelefones);
            descPane.add(iFrmPesqTelefones);

            Util.centralizar(this, iFrmPesqTelefones);
            Util.maximizar(iFrmPesqTelefones);

            iFrmPesqTelefones.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Telefones não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsTelefoneActionPerformed

    private void mnuEditTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditTelefoneActionPerformed
        try {    
            IFrmPesqTelefones iFrmPesqTelefones = new IFrmPesqTelefones(
                    new IFrmManTelefone(new Telefones()));

            descPane.remove(iFrmPesqTelefones);
            descPane.add(iFrmPesqTelefones);

            Util.centralizar(this, iFrmPesqTelefones);
            Util.maximizar(iFrmPesqTelefones);

            iFrmPesqTelefones.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Telefone não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditTelefoneActionPerformed

    private void mnuNewTelefoneTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewTelefoneTipoActionPerformed
        try {
            IFrmManTipoTelefone iFrmManTipoTelefone = 
                    new IFrmManTipoTelefone(new TiposTelefones());

            descPane.remove(iFrmManTipoTelefone);
            descPane.add(iFrmManTipoTelefone);

            Util.centralizar(this, iFrmManTipoTelefone);

            iFrmManTipoTelefone.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipo de Telefone não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewTelefoneTipoActionPerformed

    private void mnuConsTelefonesTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsTelefonesTiposActionPerformed
        try {
            IFrmPesqTiposTelefones iFrmPesqTiposTelefones = new IFrmPesqTiposTelefones(
                    new IFrmManTipoTelefone(new TiposTelefones()));

            descPane.remove(iFrmPesqTiposTelefones);
            descPane.add(iFrmPesqTiposTelefones);

            Util.centralizar(this, iFrmPesqTiposTelefones);
            Util.maximizar(iFrmPesqTiposTelefones);

            iFrmPesqTiposTelefones.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipos de Telefones não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsTelefonesTiposActionPerformed

    private void mnuEditTelefoneTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditTelefoneTipoActionPerformed
        try {
            IFrmPesqTiposTelefones iFrmPesqTiposTelefones = new IFrmPesqTiposTelefones(
                    new IFrmManTipoTelefone(new TiposTelefones()));

            descPane.remove(iFrmPesqTiposTelefones);
            descPane.add(iFrmPesqTiposTelefones);

            Util.centralizar(this, iFrmPesqTiposTelefones);
            Util.maximizar(iFrmPesqTiposTelefones);

            iFrmPesqTiposTelefones.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipo de Telefone não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditTelefoneTipoActionPerformed

    private void mnuNewSituacaoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewSituacaoClienteActionPerformed
        try {
            IFrmManSituacaoClientes iFrmManSituacaoClientes = 
                    new IFrmManSituacaoClientes(new SituacoesClientes());

            descPane.remove(iFrmManSituacaoClientes);
            descPane.add(iFrmManSituacaoClientes);

            Util.centralizar(this, iFrmManSituacaoClientes);

            iFrmManSituacaoClientes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situação do Cliente não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewSituacaoClienteActionPerformed

    private void mnuConsSituacoesClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsSituacoesClientesActionPerformed
        try {
            IFrmPesqSituacoesClientes iFrmPesqSituacoesClientes = 
                    new IFrmPesqSituacoesClientes(new IFrmManSituacaoClientes(
                            new SituacoesClientes()));

            descPane.remove(iFrmPesqSituacoesClientes);
            descPane.add(iFrmPesqSituacoesClientes);

            Util.centralizar(this, iFrmPesqSituacoesClientes);
            Util.maximizar(iFrmPesqSituacoesClientes);

            iFrmPesqSituacoesClientes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situações dos Clientes não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsSituacoesClientesActionPerformed

    private void mnuEditSituacaoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditSituacaoClienteActionPerformed
        try {
            IFrmPesqSituacoesClientes iFrmPesqSituacoesClientes = 
                    new IFrmPesqSituacoesClientes(new IFrmManSituacaoClientes(
                            new SituacoesClientes()));

            descPane.remove(iFrmPesqSituacoesClientes);
            descPane.add(iFrmPesqSituacoesClientes);

            Util.centralizar(this, iFrmPesqSituacoesClientes);
            Util.maximizar(iFrmPesqSituacoesClientes);

            iFrmPesqSituacoesClientes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Situação do Cliente não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditSituacaoClienteActionPerformed

    private void mnuNewEspecializacaoPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewEspecializacaoPessoaActionPerformed
        try {
            IFrmManPessoaEspecializacao iFrmManPessoaEspecializacao = 
                    new IFrmManPessoaEspecializacao(new PessoasEspecializacoes());

            descPane.remove(iFrmManPessoaEspecializacao);
            descPane.add(iFrmManPessoaEspecializacao);

            Util.centralizar(this, iFrmManPessoaEspecializacao);

            iFrmManPessoaEspecializacao.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Especialização de Pessoa não pode ser criada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewEspecializacaoPessoaActionPerformed

    private void mnuConsEspecializacoesPessoasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsEspecializacoesPessoasActionPerformed
        try {
            IFrmPesqPessoasEspecializacoes iFrmPesqPessoasEspecializacoes = 
                    new IFrmPesqPessoasEspecializacoes(
                            new IFrmManPessoaEspecializacao(
                                    new PessoasEspecializacoes()));

            descPane.remove(iFrmPesqPessoasEspecializacoes);
            descPane.add(iFrmPesqPessoasEspecializacoes);

            Util.centralizar(this, iFrmPesqPessoasEspecializacoes);
            Util.maximizar(iFrmPesqPessoasEspecializacoes);

            iFrmPesqPessoasEspecializacoes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Especializações de Pessoas não podem ser consultadas.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsEspecializacoesPessoasActionPerformed

    private void mnuEditEspecializacaoPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditEspecializacaoPessoaActionPerformed
        try {
            IFrmPesqPessoasEspecializacoes iFrmPesqPessoasEspecializacoes = 
                    new IFrmPesqPessoasEspecializacoes(
                            new IFrmManPessoaEspecializacao(
                                    new PessoasEspecializacoes()));

            descPane.remove(iFrmPesqPessoasEspecializacoes);
            descPane.add(iFrmPesqPessoasEspecializacoes);

            Util.centralizar(this, iFrmPesqPessoasEspecializacoes);
            Util.maximizar(iFrmPesqPessoasEspecializacoes);

            iFrmPesqPessoasEspecializacoes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Especialização de Pessoa não pode ser alterada.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditEspecializacaoPessoaActionPerformed

    private void mnuNewClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewClienteActionPerformed
        try {
            IFrmManCliente iFrmManCliente = new IFrmManCliente(new Clientes());

            descPane.remove(iFrmManCliente);
            descPane.add(iFrmManCliente);

            Util.centralizar(this, iFrmManCliente);

            iFrmManCliente.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Cliente não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewClienteActionPerformed

    private void mnuConsClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsClienteActionPerformed
        try {
            IFrmPesqClientes iFrmPesqClientes = new IFrmPesqClientes(
                    new IFrmManCliente(new Clientes()));

            descPane.remove(iFrmPesqClientes);
            descPane.add(iFrmPesqClientes);

            Util.centralizar(this, iFrmPesqClientes);
            Util.maximizar(iFrmPesqClientes);

            iFrmPesqClientes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Clientes não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsClienteActionPerformed

    private void mnuEditClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditClienteActionPerformed
        try {
            IFrmPesqClientes iFrmPesqClientes = new IFrmPesqClientes(
                    new IFrmManCliente(new Clientes()));

            descPane.remove(iFrmPesqClientes);
            descPane.add(iFrmPesqClientes);

            Util.centralizar(this, iFrmPesqClientes);
            Util.maximizar(iFrmPesqClientes);

            iFrmPesqClientes.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Cliente não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditClienteActionPerformed

    private void mnuNewPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewPlanoSaudeActionPerformed
        try {
            IFrmManPlanoSaude iFrmManPlanoSaude = new IFrmManPlanoSaude(
                    new PlanosSaude());

            descPane.remove(iFrmManPlanoSaude);
            descPane.add(iFrmManPlanoSaude);

            Util.centralizar(this, iFrmManPlanoSaude);
            
            iFrmManPlanoSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Planos de Saúde não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewPlanoSaudeActionPerformed

    private void mnuConsPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsPlanoSaudeActionPerformed
        try {
            IFrmPesqPlanosSaude iFrmPesqPlanosSaude = new IFrmPesqPlanosSaude(
                    new IFrmManPlanoSaude(new PlanosSaude()));

            descPane.remove(iFrmPesqPlanosSaude);
            descPane.add(iFrmPesqPlanosSaude);

            Util.centralizar(this, iFrmPesqPlanosSaude);
            Util.maximizar(iFrmPesqPlanosSaude);

            iFrmPesqPlanosSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Planos de Saúde não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsPlanoSaudeActionPerformed

    private void mnuEditPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditPlanoSaudeActionPerformed
        try {
            IFrmPesqPlanosSaude iFrmPesqPlanosSaude = new IFrmPesqPlanosSaude(
                    new IFrmManPlanoSaude(new PlanosSaude()));

            descPane.remove(iFrmPesqPlanosSaude);
            descPane.add(iFrmPesqPlanosSaude);

            Util.centralizar(this, iFrmPesqPlanosSaude);
            Util.maximizar(iFrmPesqPlanosSaude);

            iFrmPesqPlanosSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Plano de Saúde não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditPlanoSaudeActionPerformed

    private void mnuNewProntuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewProntuarioActionPerformed
        try {
            IFrmManProntuario iFrmManProntuario = 
                    new IFrmManProntuario(new Prontuarios());

            descPane.remove(iFrmManProntuario);
            descPane.add(iFrmManProntuario);

            Util.centralizar(this, iFrmManProntuario);

            iFrmManProntuario.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Prontuário não pode ser criado.\n\nMotivo: " + ex);
        }        
    }//GEN-LAST:event_mnuNewProntuarioActionPerformed

    private void mnuConsProntuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsProntuariosActionPerformed
        try {
            IFrmPesqProntuarios iFrmPesqProntuarios = new IFrmPesqProntuarios(
                    new IFrmManProntuario(new Prontuarios()));

            descPane.remove(iFrmPesqProntuarios);
            descPane.add(iFrmPesqProntuarios);

            Util.centralizar(this, iFrmPesqProntuarios);
            Util.maximizar(iFrmPesqProntuarios);

            iFrmPesqProntuarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Prontuários não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsProntuariosActionPerformed

    private void mnuEditProntuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditProntuarioActionPerformed
        try {
            IFrmPesqProntuarios iFrmPesqProntuarios = new IFrmPesqProntuarios(
                    new IFrmManProntuario(new Prontuarios()));

            descPane.remove(iFrmPesqProntuarios);
            descPane.add(iFrmPesqProntuarios);

            Util.centralizar(this, iFrmPesqProntuarios);
            Util.maximizar(iFrmPesqProntuarios);

            iFrmPesqProntuarios.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Prontuário não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditProntuarioActionPerformed

    private void mnuRelPessoasFisicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRelPessoasFisicasActionPerformed
       try {
            IFrmRelPessoasFisicas iFrmRelPessoasFisicas = 
                    new IFrmRelPessoasFisicas();

            descPane.remove(iFrmRelPessoasFisicas);
            descPane.add(iFrmRelPessoasFisicas);

            Util.centralizar(this, iFrmRelPessoasFisicas);

            iFrmRelPessoasFisicas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                    "Relatório de Pessoas Físicas não pode ser gerado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuRelPessoasFisicasActionPerformed

    private void mnuNewTipoPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewTipoPlanoSaudeActionPerformed
        try {
            IFrmManTipoPlanoSaude iFrmManTipoPlanoSaude = 
                    new IFrmManTipoPlanoSaude(new TiposPlanosSaude());

            descPane.remove(iFrmManTipoPlanoSaude);
            descPane.add(iFrmManTipoPlanoSaude);

            Util.centralizar(this, iFrmManTipoPlanoSaude);

            iFrmManTipoPlanoSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipo de Plano de Saúde não pode ser criado.\n\nMotivo: " + ex);
        }     
    }//GEN-LAST:event_mnuNewTipoPlanoSaudeActionPerformed

    private void mnuConsTiposPalanosSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsTiposPalanosSaudeActionPerformed
        try {
            IFrmPesqTiposPlanosSaude iFrmPesqTiposPlanosSaude = 
                    new IFrmPesqTiposPlanosSaude(new IFrmManTipoPlanoSaude(
                            new TiposPlanosSaude()));

            descPane.remove(iFrmPesqTiposPlanosSaude);
            descPane.add(iFrmPesqTiposPlanosSaude);

            Util.centralizar(this, iFrmPesqTiposPlanosSaude);
            Util.maximizar(iFrmPesqTiposPlanosSaude);

            iFrmPesqTiposPlanosSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipos de Planos de Saúde não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsTiposPalanosSaudeActionPerformed

    private void mnuEditTipoPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditTipoPlanoSaudeActionPerformed
        try {
            IFrmPesqTiposPlanosSaude iFrmPesqTiposPlanosSaude = 
                    new IFrmPesqTiposPlanosSaude(new IFrmManTipoPlanoSaude(
                            new TiposPlanosSaude()));

            descPane.remove(iFrmPesqTiposPlanosSaude);
            descPane.add(iFrmPesqTiposPlanosSaude);

            Util.centralizar(this, iFrmPesqTiposPlanosSaude);
            Util.maximizar(iFrmPesqTiposPlanosSaude);

            iFrmPesqTiposPlanosSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipo de Plano de Saúde não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditTipoPlanoSaudeActionPerformed

    private void mnuNewTipoPlanoSaudeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewTipoPlanoSaudeClienteActionPerformed
        try {
            IFrmManClienteTipoPlano iFrmManClienteTipoPlano = 
                    new IFrmManClienteTipoPlano(new ClientesTiposPlanos());

            descPane.remove(iFrmManClienteTipoPlano);
            descPane.add(iFrmManClienteTipoPlano);

            Util.centralizar(this, iFrmManClienteTipoPlano);

            iFrmManClienteTipoPlano.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipo de Plano de Saúde do Cliente não pode ser criado.\n\nMotivo: " + ex);
        } 
    }//GEN-LAST:event_mnuNewTipoPlanoSaudeClienteActionPerformed

    private void mnuConsTiposPlanosSaudeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsTiposPlanosSaudeClienteActionPerformed
        try {
            IFrmPesqClientesTiposPlanos iFrmPesqClientesTiposPlanos = 
                    new IFrmPesqClientesTiposPlanos(new IFrmManClienteTipoPlano(
                            new ClientesTiposPlanos()));

            descPane.remove(iFrmPesqClientesTiposPlanos);
            descPane.add(iFrmPesqClientesTiposPlanos);

            Util.centralizar(this, iFrmPesqClientesTiposPlanos);
            Util.maximizar(iFrmPesqClientesTiposPlanos);

            iFrmPesqClientesTiposPlanos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipos de Planos de Saúde do Cliente não podem ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsTiposPlanosSaudeClienteActionPerformed

    private void mnuEditTipoPlanoSaudeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditTipoPlanoSaudeClienteActionPerformed
        try {
            IFrmPesqClientesTiposPlanos iFrmPesqClientesTiposPlanos = 
                    new IFrmPesqClientesTiposPlanos(new IFrmManClienteTipoPlano(
                            new ClientesTiposPlanos()));

            descPane.remove(iFrmPesqClientesTiposPlanos);
            descPane.add(iFrmPesqClientesTiposPlanos);

            Util.centralizar(this, iFrmPesqClientesTiposPlanos);
            Util.maximizar(iFrmPesqClientesTiposPlanos);

            iFrmPesqClientesTiposPlanos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Tipo de Plano de Saúde do Cliente não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditTipoPlanoSaudeClienteActionPerformed

    private void mnuNewProfissionalPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewProfissionalPlanoSaudeActionPerformed
        try {
            IFrmManProfissionalPlanoSaude iFrmManProfissionalPlanoSaude =
                    new IFrmManProfissionalPlanoSaude(
                                    new ProfissionaisPlanosSaude());

            descPane.remove(iFrmManProfissionalPlanoSaude);
            descPane.add(iFrmManProfissionalPlanoSaude);

            Util.centralizar(this, iFrmManProfissionalPlanoSaude);

            iFrmManProfissionalPlanoSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Plano de Saúde do Profissional não pode ser criado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuNewProfissionalPlanoSaudeActionPerformed

    private void mnuConsProfissionaisPlanosSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsProfissionaisPlanosSaudeActionPerformed
        try {
            IFrmPesqProfissionaisPlanosSaude iFrmPesqProfissionaisPlanosSaude = 
                    new IFrmPesqProfissionaisPlanosSaude(
                            new IFrmManProfissionalPlanoSaude(
                                    new ProfissionaisPlanosSaude()));

            descPane.remove(iFrmPesqProfissionaisPlanosSaude);
            descPane.add(iFrmPesqProfissionaisPlanosSaude);

            Util.centralizar(this, iFrmPesqProfissionaisPlanosSaude);
            Util.maximizar(iFrmPesqProfissionaisPlanosSaude);

            iFrmPesqProfissionaisPlanosSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Planos de Saúde dos Profissionais não pode ser consultados.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsProfissionaisPlanosSaudeActionPerformed

    private void mnuEditProfissionalPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuEditProfissionalPlanoSaudeActionPerformed
        try {
            IFrmPesqProfissionaisPlanosSaude iFrmPesqProfissionaisPlanosSaude = 
                    new IFrmPesqProfissionaisPlanosSaude(
                            new IFrmManProfissionalPlanoSaude(
                                    new ProfissionaisPlanosSaude()));

            descPane.remove(iFrmPesqProfissionaisPlanosSaude);
            descPane.add(iFrmPesqProfissionaisPlanosSaude);

            Util.centralizar(this, iFrmPesqProfissionaisPlanosSaude);
            Util.maximizar(iFrmPesqProfissionaisPlanosSaude);

            iFrmPesqProfissionaisPlanosSaude.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Plano de Saúde do Profissional não pode ser alterado.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuEditProfissionalPlanoSaudeActionPerformed

    private void mnuGlossarioDoencasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGlossarioDoencasActionPerformed
        try {
            IFrmPesqGlossarioDoencas iFrmPesqGlossarioDoencas = 
                    new IFrmPesqGlossarioDoencas(new IFrmManGlossarioDoenca(
                            new GlossarioDoencas()));

            descPane.remove(iFrmPesqGlossarioDoencas);
            descPane.add(iFrmPesqGlossarioDoencas);

            Util.centralizar(this, iFrmPesqGlossarioDoencas);
            Util.maximizar(iFrmPesqGlossarioDoencas);

            iFrmPesqGlossarioDoencas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Glossário de Doenças não está acessível.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuGlossarioDoencasActionPerformed

    private void mnuMedicamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMedicamentosActionPerformed
        try {
            IFrmPesqMedicamentos iFrmPesqMedicamentos = 
                    new IFrmPesqMedicamentos(new IFrmManMedicamento(
                            new Medicamentos()));

            descPane.remove(iFrmPesqMedicamentos);
            descPane.add(iFrmPesqMedicamentos);

            Util.centralizar(this, iFrmPesqMedicamentos);
            Util.maximizar(iFrmPesqMedicamentos);

            iFrmPesqMedicamentos.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Medicamentos não estão acessíveis.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuMedicamentosActionPerformed

    private void mnuSintomasApresentadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSintomasApresentadosActionPerformed
        try {
            IFrmPesqSintomasApresentados iFrmPesqSintomasApresentados = 
                    new IFrmPesqSintomasApresentados(
                            new IFrmManSintomaApresentado(
                                    new SintomasApresentados()));

            descPane.remove(iFrmPesqSintomasApresentados);
            descPane.add(iFrmPesqSintomasApresentados);

            Util.centralizar(this, iFrmPesqSintomasApresentados);
            Util.maximizar(iFrmPesqSintomasApresentados);

            iFrmPesqSintomasApresentados.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Sintomas Apresentados não estão acessíveis.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuSintomasApresentadosActionPerformed

    private void mnuConsMotivosAgendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsMotivosAgendasActionPerformed
        try {
            IFrmPesqMotivosAgendas iFrmPesqMotivosAgendas = 
                    new IFrmPesqMotivosAgendas(new IFrmManMotivoAgenda(
                            new MotivosAgendas()));

            descPane.remove(iFrmPesqMotivosAgendas);
            descPane.add(iFrmPesqMotivosAgendas);

            Util.centralizar(this, iFrmPesqMotivosAgendas);
            Util.maximizar(iFrmPesqMotivosAgendas);

            iFrmPesqMotivosAgendas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Motivos de Agendas não estão acessíveis.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsMotivosAgendasActionPerformed

    private void mnuConsAgendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConsAgendasActionPerformed
        try {
            IFrmPesqAgendas iFrmPesqAgendas = new IFrmPesqAgendas(
                    new IFrmManAgenda(new Agendas()));

            descPane.remove(iFrmPesqAgendas);
            descPane.add(iFrmPesqAgendas);

            Util.centralizar(this, iFrmPesqAgendas);
            Util.maximizar(iFrmPesqAgendas);

            iFrmPesqAgendas.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.getParent(),
                "Agendas não estão acessíveis.\n\nMotivo: " + ex);
        }
    }//GEN-LAST:event_mnuConsAgendasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane descPane;
    private javax.swing.JPopupMenu.Separator jSeparator1Agendas;
    private javax.swing.JPopupMenu.Separator jSeparator1Arquivo;
    private javax.swing.JPopupMenu.Separator jSeparator1Cons;
    private javax.swing.JPopupMenu.Separator jSeparator1ConsClientes;
    private javax.swing.JPopupMenu.Separator jSeparator1ConsEnderecos;
    private javax.swing.JPopupMenu.Separator jSeparator1ConsFuncionarios;
    private javax.swing.JPopupMenu.Separator jSeparator1ConsPessoas;
    private javax.swing.JPopupMenu.Separator jSeparator1ConsPlanosSaude;
    private javax.swing.JPopupMenu.Separator jSeparator1ConsProfissionais;
    private javax.swing.JPopupMenu.Separator jSeparator1ConsTelefones;
    private javax.swing.JPopupMenu.Separator jSeparator1ConsUsuarios;
    private javax.swing.JPopupMenu.Separator jSeparator1Edit;
    private javax.swing.JPopupMenu.Separator jSeparator1EditClientes;
    private javax.swing.JPopupMenu.Separator jSeparator1EditEnderecos;
    private javax.swing.JPopupMenu.Separator jSeparator1EditFuncionarios;
    private javax.swing.JPopupMenu.Separator jSeparator1EditPessoas;
    private javax.swing.JPopupMenu.Separator jSeparator1EditPlanosSaude;
    private javax.swing.JPopupMenu.Separator jSeparator1EditProfissionais;
    private javax.swing.JPopupMenu.Separator jSeparator1EditTelefones;
    private javax.swing.JPopupMenu.Separator jSeparator1EditUsuarios;
    private javax.swing.JPopupMenu.Separator jSeparator1New;
    private javax.swing.JPopupMenu.Separator jSeparator1NewClientes;
    private javax.swing.JPopupMenu.Separator jSeparator1NewEnderecos;
    private javax.swing.JPopupMenu.Separator jSeparator1NewFuncionarios;
    private javax.swing.JPopupMenu.Separator jSeparator1NewPessoas;
    private javax.swing.JPopupMenu.Separator jSeparator1NewPlanosSaude;
    private javax.swing.JPopupMenu.Separator jSeparator1NewProfissionais;
    private javax.swing.JPopupMenu.Separator jSeparator1NewTelefones;
    private javax.swing.JPopupMenu.Separator jSeparator1NewUsuarios;
    private javax.swing.JPopupMenu.Separator jSeparator2Arquivo;
    private javax.swing.JPopupMenu.Separator jSeparator2Cons;
    private javax.swing.JPopupMenu.Separator jSeparator2ConsEnderecos;
    private javax.swing.JPopupMenu.Separator jSeparator2Edit;
    private javax.swing.JPopupMenu.Separator jSeparator2EditEnderecos;
    private javax.swing.JPopupMenu.Separator jSeparator2New;
    private javax.swing.JPopupMenu.Separator jSeparator2NewEnderecos;
    private javax.swing.JPopupMenu.Separator jSeparator3Cons;
    private javax.swing.JPopupMenu.Separator jSeparator3Edit;
    private javax.swing.JPopupMenu.Separator jSeparator3New;
    private javax.swing.JPopupMenu.Separator jSeparator4Cons;
    private javax.swing.JPopupMenu.Separator jSeparator4Edit;
    private javax.swing.JPopupMenu.Separator jSeparator4New;
    private javax.swing.JLabel lblImagemFundo;
    private javax.swing.JMenu mnuAgendas;
    private javax.swing.JMenu mnuAjuda;
    private javax.swing.JMenuBar mnuBar;
    private javax.swing.JMenuItem mnuConsAcoes;
    private javax.swing.JMenuItem mnuConsAgendas;
    private javax.swing.JMenuItem mnuConsBairros;
    private javax.swing.JMenuItem mnuConsCidades;
    private javax.swing.JMenuItem mnuConsCliente;
    private javax.swing.JMenu mnuConsClientes;
    private javax.swing.JMenuItem mnuConsEndereco;
    private javax.swing.JMenu mnuConsEnderecos;
    private javax.swing.JMenuItem mnuConsEnderecosTipos;
    private javax.swing.JMenuItem mnuConsEspecializacoes;
    private javax.swing.JMenuItem mnuConsEspecializacoesPessoas;
    private javax.swing.JMenuItem mnuConsEstados;
    private javax.swing.JMenuItem mnuConsFuncionario;
    private javax.swing.JMenu mnuConsFuncionaruios;
    private javax.swing.JMenuItem mnuConsFuncoes;
    private javax.swing.JMenuItem mnuConsMotivosAgendas;
    private javax.swing.JMenuItem mnuConsPaises;
    private javax.swing.JMenu mnuConsPessoas;
    private javax.swing.JMenuItem mnuConsPessoasFisicas;
    private javax.swing.JMenuItem mnuConsPessoasJuridicas;
    private javax.swing.JMenuItem mnuConsPlanoSaude;
    private javax.swing.JMenu mnuConsPlanosSaude;
    private javax.swing.JMenu mnuConsProfissionais;
    private javax.swing.JMenuItem mnuConsProfissionaisPlanosSaude;
    private javax.swing.JMenuItem mnuConsProfissionaisTurnos;
    private javax.swing.JMenuItem mnuConsProfissional;
    private javax.swing.JMenuItem mnuConsProntuarios;
    private javax.swing.JMenuItem mnuConsSituacoesClientes;
    private javax.swing.JMenuItem mnuConsSituacoesFuncionarios;
    private javax.swing.JMenuItem mnuConsSituacoesPessoas;
    private javax.swing.JMenuItem mnuConsTelefone;
    private javax.swing.JMenu mnuConsTelefones;
    private javax.swing.JMenuItem mnuConsTelefonesTipos;
    private javax.swing.JMenuItem mnuConsTiposPalanosSaude;
    private javax.swing.JMenuItem mnuConsTiposPlanosSaudeCliente;
    private javax.swing.JMenuItem mnuConsUsuario;
    private javax.swing.JMenu mnuConsUsuarios;
    private javax.swing.JMenuItem mnuConsUsuariosAcoes;
    private javax.swing.JMenu mnuConsultar;
    private javax.swing.JMenuItem mnuEditAcao;
    private javax.swing.JMenuItem mnuEditAparencia;
    private javax.swing.JMenuItem mnuEditBackup;
    private javax.swing.JMenuItem mnuEditBairro;
    private javax.swing.JMenuItem mnuEditCidade;
    private javax.swing.JMenuItem mnuEditCliente;
    private javax.swing.JMenu mnuEditClientes;
    private javax.swing.JMenuItem mnuEditEndereco;
    private javax.swing.JMenuItem mnuEditEnderecoTipo;
    private javax.swing.JMenu mnuEditEnderecos;
    private javax.swing.JMenuItem mnuEditEspecializacao;
    private javax.swing.JMenuItem mnuEditEspecializacaoPessoa;
    private javax.swing.JMenuItem mnuEditEstado;
    private javax.swing.JMenuItem mnuEditFuncao;
    private javax.swing.JMenuItem mnuEditFuncionario;
    private javax.swing.JMenu mnuEditFuncionarios;
    private javax.swing.JMenuItem mnuEditPais;
    private javax.swing.JMenuItem mnuEditPessoaFisica;
    private javax.swing.JMenuItem mnuEditPessoaJuridica;
    private javax.swing.JMenu mnuEditPessoas;
    private javax.swing.JMenuItem mnuEditPlanoSaude;
    private javax.swing.JMenu mnuEditPlanosSaude;
    private javax.swing.JMenu mnuEditProfissionais;
    private javax.swing.JMenuItem mnuEditProfissional;
    private javax.swing.JMenuItem mnuEditProfissionalPlanoSaude;
    private javax.swing.JMenuItem mnuEditProfissionalTurnos;
    private javax.swing.JMenuItem mnuEditProntuario;
    private javax.swing.JMenuItem mnuEditSituacaoCliente;
    private javax.swing.JMenuItem mnuEditSituacaoFuncionarios;
    private javax.swing.JMenuItem mnuEditSituacaoPessoa;
    private javax.swing.JMenuItem mnuEditTelefone;
    private javax.swing.JMenuItem mnuEditTelefoneTipo;
    private javax.swing.JMenu mnuEditTelefones;
    private javax.swing.JMenuItem mnuEditTipoPlanoSaude;
    private javax.swing.JMenuItem mnuEditTipoPlanoSaudeCliente;
    private javax.swing.JMenuItem mnuEditUsuario;
    private javax.swing.JMenuItem mnuEditUsuarioAcoes;
    private javax.swing.JMenu mnuEditUsuarios;
    private javax.swing.JMenu mnuEditar;
    private javax.swing.JMenu mnuFerramentas;
    private javax.swing.JMenuItem mnuGlossarioDoencas;
    private javax.swing.JMenuItem mnuMedicamentos;
    private javax.swing.JMenuItem mnuNewAcao;
    private javax.swing.JMenuItem mnuNewBairro;
    private javax.swing.JMenuItem mnuNewCidade;
    private javax.swing.JMenuItem mnuNewCliente;
    private javax.swing.JMenu mnuNewClientes;
    private javax.swing.JMenuItem mnuNewEndereco;
    private javax.swing.JMenuItem mnuNewEnderecoTipo;
    private javax.swing.JMenu mnuNewEnderecos;
    private javax.swing.JMenuItem mnuNewEspecializacao;
    private javax.swing.JMenuItem mnuNewEspecializacaoPessoa;
    private javax.swing.JMenuItem mnuNewEstado;
    private javax.swing.JMenuItem mnuNewFuncao;
    private javax.swing.JMenuItem mnuNewFuncionario;
    private javax.swing.JMenu mnuNewFuncionarios;
    private javax.swing.JMenuItem mnuNewPais;
    private javax.swing.JMenuItem mnuNewPessoaFisica;
    private javax.swing.JMenuItem mnuNewPessoaJuridica;
    private javax.swing.JMenu mnuNewPessoas;
    private javax.swing.JMenuItem mnuNewPlanoSaude;
    private javax.swing.JMenu mnuNewPlanosSaude;
    private javax.swing.JMenu mnuNewProfissionais;
    private javax.swing.JMenuItem mnuNewProfissional;
    private javax.swing.JMenuItem mnuNewProfissionalPlanoSaude;
    private javax.swing.JMenuItem mnuNewProfissionalTurnos;
    private javax.swing.JMenuItem mnuNewProntuario;
    private javax.swing.JMenuItem mnuNewSituacaoCliente;
    private javax.swing.JMenuItem mnuNewSituacaoFuncionarios;
    private javax.swing.JMenuItem mnuNewSituacaoPessoas;
    private javax.swing.JMenuItem mnuNewTelefone;
    private javax.swing.JMenuItem mnuNewTelefoneTipo;
    private javax.swing.JMenu mnuNewTelefones;
    private javax.swing.JMenuItem mnuNewTipoPlanoSaude;
    private javax.swing.JMenuItem mnuNewTipoPlanoSaudeCliente;
    private javax.swing.JMenuItem mnuNewUsuario;
    private javax.swing.JMenuItem mnuNewUsuarioAcoes;
    private javax.swing.JMenu mnuNewUsuarios;
    private javax.swing.JMenu mnuNovo;
    private javax.swing.JMenu mnuPesquisar;
    private javax.swing.JMenuItem mnuRelPessoasFisicas;
    private javax.swing.JMenu mnuRelatorios;
    private javax.swing.JMenu mnuSAMHO;
    private javax.swing.JMenuItem mnuSair;
    private javax.swing.JMenuItem mnuSintomasApresentados;
    private javax.swing.JMenuItem mnuSobreSANHO;
    // End of variables declaration//GEN-END:variables
}
