-----------------------------------------------------------------------
-- Criação de tabelas do sistema de Agenda Médico/Hospitalar - SAMHO --
-----------------------------------------------------------------------
-- *** Iniciar o sistema utilizando o usuário ADMINISTRADOR e senha ADMIN.

-- Criação das tabelas de apoio para pessoas
	-- Criação tabela situações de pessoas
	CREATE TABLE situacoes_pessoas(
		id_situacao BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		observacoes TEXT,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_situacoes_pessoas PRIMARY KEY(id_situacao)
	);

	-- Criação tabela pessoas
	CREATE TABLE pessoas(
		id_pessoa BIGSERIAL NOT NULL,
		cod_situacao BIGSERIAL NOT NULL,
		nome VARCHAR(50) NOT NULL,
		sobrenome VARCHAR(50) NOT NULL,
		observacoes TEXT,

		CONSTRAINT pk_pessoas PRIMARY KEY(id_pessoa),
		CONSTRAINT fk_pessoas_codsituacao FOREIGN KEY(cod_situacao)
			REFERENCES situacoes_pessoas(id_situacao)
	);

	-- Criação tabela pessoas_fisicas
	CREATE TABLE pessoas_fisicas(
		cod_pessoa BIGSERIAL NOT NULL,
		cpf BIGSERIAL NOT NULL,
		rg BIGSERIAL NOT NULL,
		nome_mae VARCHAR(100) NOT NULL,
		data_nascimento DATE NOT NULL,
		sexo char(1) NOT NULL,

		CONSTRAINT pk_pessoas_fisicas PRIMARY KEY(cod_pessoa),
		CONSTRAINT fk_pessoas_fisicas_codpessoa FOREIGN KEY(cod_pessoa)
			REFERENCES pessoas(id_pessoa)
	);

	-- Criação tabela pessoas_juridicas
	CREATE TABLE pessoas_juridicas(
		cod_pessoa BIGSERIAL NOT NULL,
		cnpj BIGSERIAL NOT NULL,
		ie BIGSERIAL NOT NULL,
		data_fundacao DATE NOT NULL,

		CONSTRAINT pk_pessoas_juridicas PRIMARY KEY(cod_pessoa),
		CONSTRAINT fk_pessoas_juridicas_codpessoa FOREIGN KEY(cod_pessoa)
			REFERENCES pessoas(id_pessoa)
	);

-- Criação das tabelas de apoio para endereços
	-- Criação tabela paises
	CREATE TABLE paises (
		id_pais BIGSERIAL NOT NULL,
		codigo_ibge INT NOT NULL,
		sigla VARCHAR(3),
		descricao VARCHAR(50) NOT NULL,

		CONSTRAINT pk_paises PRIMARY KEY (id_pais)
	);

	-- Criação tabela estados
	CREATE TABLE estados(
		id_estado BIGSERIAL NOT NULL,
		cod_pais BIGSERIAL NOT NULL,
		codigo_ibge INT NOT NULL,
		sigla CHAR(2) NOT NULL,
		descricao VARCHAR(50) NOT NULL,

		CONSTRAINT pk_estados PRIMARY KEY (id_estado),
		CONSTRAINT fk_estados_codpais FOREIGN KEY (cod_pais)
			REFERENCES paises(id_pais)
	);

	-- Criação tabela cidades
	CREATE TABLE cidades (
		id_cidade BIGSERIAL NOT NULL,
		cod_estado BIGSERIAL NOT NULL,
		codigo_ibge INT NOT NULL,
		descricao VARCHAR(100) NOT NULL,

		CONSTRAINT pk_cidades PRIMARY KEY (id_cidade),
		CONSTRAINT fk_cidades_codestado FOREIGN KEY (cod_estado)
			REFERENCES estados (id_estado)
	);

	-- Criação tabela bairros
	CREATE TABLE bairros(
		id_bairro BIGSERIAL NOT NULL,
		cod_cidade BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,

		CONSTRAINT pk_bairros PRIMARY KEY(id_bairro),
		CONSTRAINT fk_bairros_codcidade FOREIGN KEY(cod_cidade)
			REFERENCES cidades(id_cidade)
	);

	-- Criação tabela tipos_enderecos
	CREATE TABLE tipos_enderecos(
		id_tipo_endereco BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_tipos_enderecos PRIMARY KEY(id_tipo_endereco)
	);

	-- Criação tabela enderecos
	CREATE TABLE enderecos(
		id_endereco BIGSERIAL NOT NULL,
		cod_pessoa BIGSERIAL NOT NULL,
		cod_tipo_endereco BIGSERIAL NOT NULL,
		cod_bairro BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		cep BIGSERIAL NOT NULL,
		complemento VARCHAR(100) NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_enderecos PRIMARY KEY(id_endereco),
		CONSTRAINT fk_enderecos_codpessoa FOREIGN KEY(cod_pessoa)
			REFERENCES pessoas(id_pessoa),
		CONSTRAINT fk_enderecos_codtipoendereco FOREIGN KEY(cod_tipo_endereco)
			REFERENCES tipos_enderecos(id_tipo_endereco),
		CONSTRAINT fk_enderecos_codbairro FOREIGN KEY(cod_bairro)
			REFERENCES bairros(id_bairro)
	);

-- Criação das tabelas de apoio para telefones
	-- Criação tabela tipos_telefones
	CREATE TABLE tipos_telefones(
		id_tipo_telefone BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_tipos_telefones PRIMARY KEY(id_tipo_telefone)
	);

	-- Criação tabela telefones
	CREATE TABLE telefones(
		id_telefone BIGSERIAL NOT NULL,
		cod_pessoa BIGSERIAL NOT NULL,
		cod_tipo_telefone BIGSERIAL NOT NULL,
		numero BIGSERIAL NOT NULL,
		ramal INT NOT NULL,
		contato VARCHAR(100) NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_telefones PRIMARY KEY(id_telefone),
		CONSTRAINT fk_telefones_codpessoa FOREIGN KEY(cod_pessoa)
			REFERENCES pessoas(id_pessoa),
		CONSTRAINT fk_telefones_codtipotelefone FOREIGN KEY(cod_tipo_telefone)
			REFERENCES tipos_telefones(id_tipo_telefone)
	);

-- Criação das tabelas de apoio para funcionários
	-- Criação tabela situações de funcionários
	CREATE TABLE situacoes_funcionarios(
		id_situacao BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		observacoes TEXT,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_situacoes_funcionarios PRIMARY KEY(id_situacao)
	);

	-- Criação tabela funcoes
	CREATE TABLE funcoes(
		id_funcao BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_funcoes PRIMARY KEY(id_funcao)
	);

	-- Criação tabela funcionários
	CREATE TABLE funcionarios(
		id_funcionario BIGSERIAL NOT NULL,
		cod_situacao BIGSERIAL NOT NULL,
		cod_pessoa BIGSERIAL NOT NULL,
		cod_funcao BIGSERIAL NOT NULL,
		vlr_salario DECIMAL(13,2) NOT NULL,
		dias_trabalhados_mes INT NOT NULL DEFAULT 1,
		carga_horaria DECIMAL(20,15) NOT NULL,
		data_admissao DATE NOT NULL,
		data_demissao DATE,
		numero_clt INT,

		CONSTRAINT pk_funcionarios PRIMARY KEY(id_funcionario),
		CONSTRAINT fk_funcionarios_codsituacao FOREIGN KEY(cod_situacao)
			REFERENCES situacoes_funcionarios(id_situacao),
		CONSTRAINT fk_funcionarios_codpessoa FOREIGN KEY(cod_pessoa)
			REFERENCES pessoas(id_pessoa),
		CONSTRAINT fk_funcionarios_codfuncao FOREIGN KEY(cod_funcao)
			REFERENCES funcoes(id_funcao),
		CONSTRAINT chk_data_demicao CHECK(data_admissao <= data_demissao)
	);

-- Criação das tabelas de apoio para usuarios
	-- Criação tabela acoes
	CREATE TABLE acoes(
		id_acao BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,

		CONSTRAINT pk_acoes PRIMARY KEY(id_acao)
	);

	-- Criação tabela usuarios
	CREATE TABLE usuarios(
		id_usuario BIGSERIAL NOT NULL,
		cod_funcionario BIGSERIAL NOT NULL,
		login VARCHAR(45) NOT NULL,
		senha VARCHAR(45) NOT NULL,
		administrador BOOLEAN NOT NULL DEFAULT 'f',

		CONSTRAINT pk_usuarios PRIMARY KEY(id_usuario),
		CONSTRAINT fk_usuarios_codfuncionario FOREIGN KEY(cod_funcionario)
			REFERENCES funcionarios(id_funcionario)
	);

	-- Criação tabela acoes_usuarios
	CREATE TABLE acoes_usuarios(
		id_acao_usuario BIGSERIAL NOT NULL,
		cod_acao BIGSERIAL NOT NULL,
		cod_usuario BIGSERIAL NOT NULL,
		incluir BOOLEAN NOT NULL DEFAULT 'f',
		alterar BOOLEAN NOT NULL DEFAULT 'f',
		excluir BOOLEAN NOT NULL DEFAULT 'f',
		consultar BOOLEAN NOT NULL DEFAULT 'f',

		CONSTRAINT pk_acoes_usuarios PRIMARY KEY(id_acao_usuario),
		CONSTRAINT fk_acoes_usuarios_codacao FOREIGN KEY(cod_acao)
			REFERENCES acoes(id_acao),
		CONSTRAINT fk_acoes_usuarios_codusuario FOREIGN KEY(cod_usuario)
			REFERENCES usuarios(id_usuario)
	);

-- Criação das tabelas de apoio para profissionais
	-- Criação tabela especializaçõeses de profissionais
	CREATE TABLE especializacoes(
		id_especializacao BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		observacoes TEXT,

		CONSTRAINT pk_especializacoes PRIMARY KEY(id_especializacao)
	);

	-- Criação tabela profissionais
	CREATE TABLE profissionais(
		id_profissional BIGSERIAL NOT NULL,
		cod_pessoa BIGSERIAL NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_profissionais PRIMARY KEY(id_profissional),
		CONSTRAINT fk_profissionais_codpessoa FOREIGN KEY (cod_pessoa) REFERENCES pessoas (id_pessoa)
	);

	-- Criação tabela profissionais_turnos
	CREATE TABLE profissionais_turnos(
		id_profissional_turno BIGSERIAL NOT NULL,
		cod_profissional BIGSERIAL NOT NULL,
		dia_semana INT NOT NULL,
		madrugada BOOLEAN NOT NULL DEFAULT 'f',
		manha BOOLEAN NOT NULL DEFAULT 'f',
		tarde BOOLEAN NOT NULL DEFAULT 'f',
		noite BOOLEAN NOT NULL DEFAULT 'f',
		atende_extra BOOLEAN NOT NULL DEFAULT 'f',
		atende_feriado BOOLEAN NOT NULL DEFAULT 'f',
		
		CONSTRAINT pk_profissionais_turnos PRIMARY KEY(id_profissional_turno),
		CONSTRAINT fk_profissionais_turnos_codprofissional FOREIGN KEY(cod_profissional)
			REFERENCES profissionais(id_profissional)
	);
	
	COMMENT ON COLUMN profissionais_turnos.dia_semana IS 'Os dias da semana começam no domingo e este campo tem o valor 1.';
	COMMENT ON COLUMN profissionais_turnos.madrugada IS 'Entre 00:00 e 05:59';
	COMMENT ON COLUMN profissionais_turnos.manha IS 'Entre às 06:00 e 11:59';
	COMMENT ON COLUMN profissionais_turnos.tarde IS 'Entre o 12:00 e 17:59';
	COMMENT ON COLUMN profissionais_turnos.noite IS 'Entre às 18:00 e 23:59';


	-- Criação tabela especializaçõeses por profissionais
	CREATE TABLE pessoas_especializacoes(
		id_pessoa_especializacao BIGSERIAL NOT NULL,
		cod_pessoa BIGSERIAL NOT NULL,
		cod_especializacao BIGSERIAL NOT NULL,
		instituicao VARCHAR(100) NOT NULL,
		num_registro INT NOT NULL,
		data_conclusao DATE NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',
		observacoes TEXT,

		CONSTRAINT pk_pessoas_especializacoes PRIMARY KEY(id_pessoa_especializacao),
		CONSTRAINT fk_pessoas_especializacoes_codpessoa FOREIGN KEY (cod_pessoa) REFERENCES pessoas (id_pessoa),
		CONSTRAINT fk_pessoas_especializacoes_codespecializacao FOREIGN KEY(cod_especializacao)
			REFERENCES especializacoes(id_especializacao)
	);
	

-- Criação das tabelas de apoio para clientes
	-- Criação tabela situações de clientes
	CREATE TABLE situacoes_clientes(
		id_situacao BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		observacoes TEXT,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_situacoes_clientes PRIMARY KEY(id_situacao)
	);

-- Criação tabela clientes
	-- cod_prontuario não uma FK que pode ser nula, porém se existir deve constar na tabela de prontuários
	CREATE TABLE clientes(
		id_cliente BIGSERIAL NOT NULL,
		cod_situacao BIGSERIAL NOT NULL,
		cod_pessoa BIGSERIAL NOT NULL,
		data_cadastro DATE NOT NULL,
		observacoes TEXT,

		CONSTRAINT pk_clientes PRIMARY KEY(id_cliente),
		CONSTRAINT fk_clientes_codsituacao FOREIGN KEY(cod_situacao)
			REFERENCES situacoes_clientes(id_situacao),
		CONSTRAINT fk_clientes_codpessoa FOREIGN KEY(cod_pessoa)
			REFERENCES pessoas(id_pessoa)
	);

-- Criação das tabelas de apoio para o agendamento de consultas
	-- Criação da tabela de prontuários
	CREATE TABLE prontuarios(
		id_prontuario BIGSERIAL NOT NULL,
		cod_cliente BIGSERIAL NOT NULL,
		motivo_consulta VARCHAR(1000),
		diagnostico TEXT,
		data_consulta DATE NOT NULL,
		parecer_profissional TEXT NOT NULL,
		observacoes TEXT,
		deve_retornar BOOLEAN NOT NULL DEFAULT 'f',

		CONSTRAINT pk_prontuarios PRIMARY KEY (id_prontuario),
		CONSTRAINT fk_prontuarios_codcliente FOREIGN KEY(cod_cliente)
			REFERENCES clientes(id_cliente)
	);

	-- Criação da tabela de planos de saúde
	CREATE TABLE planos_saude(
		id_plano_saude BIGSERIAL NOT NULL,
		descricao VARCHAR(200) NOT NULL,   
		observacoes TEXT,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_planos_saude PRIMARY KEY (id_plano_saude)
	);

	-- Criação da tabela de tipos de planos de saúde
	CREATE TABLE tipos_planos(
		id_tipo_plano BIGSERIAL NOT NULL,
		cod_plano_saude BIGSERIAL NOT NULL,
		descricao VARCHAR(1000) NOT NULL,
		vlr_diferenca DECIMAL(13,2) NOT NULL,
		carencia_meses INT NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 'f',

		CONSTRAINT pk_tipos_planos PRIMARY KEY (id_tipo_plano),
		CONSTRAINT fk_tipos_planos_codplanosaude FOREIGN KEY(cod_plano_saude)
			REFERENCES planos_saude(id_plano_saude)
	);

	-- Criação da tabela de tipos de planos de saúde do cliente
	CREATE TABLE clientes_tipos_planos(
		id_cliente_tipo_plano BIGSERIAL NOT NULL,
		cod_cliente BIGSERIAL NOT NULL,
		cod_tipo_plano BIGSERIAL NOT NULL,
		data_adesao DATE NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_clientes_tipos_planos PRIMARY KEY (id_cliente_tipo_plano),
		CONSTRAINT fk_clientes_tipos_planos_codcliente FOREIGN KEY(cod_cliente)
			REFERENCES clientes(id_cliente),
		CONSTRAINT fk_clientes_tipos_planos_codtipoplano FOREIGN KEY(cod_tipo_plano)
			REFERENCES tipos_planos(id_tipo_plano)
	);

	-- Criação da tabela de tipos de planos de saúde atendidos pelos profissionais
	CREATE TABLE profissionais_planos_saude(
		id_profissional_plano_saude BIGSERIAL NOT NULL,
		cod_profissional BIGSERIAL NOT NULL,
		cod_plano_saude BIGSERIAL NOT NULL,
		data_adesao DATE NOT NULL,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_profissionais_planos_saude PRIMARY KEY (id_profissional_plano_saude),
		CONSTRAINT fk_profissionais_planos_saude_codprofissional FOREIGN KEY(cod_profissional)
			REFERENCES profissionais(id_profissional),
		CONSTRAINT fk_profissionais_planos_saude_codplanosaude FOREIGN KEY(cod_plano_saude)
			REFERENCES planos_saude(id_plano_saude)
	);

	-- Criação da tabela de glossario de doenças
	CREATE TABLE glossario_doencas(
		id_glossario_doenca BIGSERIAL NOT NULL,
		cod_profissional_cadastro BIGSERIAL NOT NULL,
		CID_10 VARCHAR(45) NOT NULL DEFAULT 'XXXX',
		descricao VARCHAR(1000) NOT NULL,
		dias_tratamento INT NOT NULL DEFAULT 999999,
		data_registro DATE NOT NULL,
		observacoes TEXT,
		revisao BIGSERIAL NOT NULL,

		CONSTRAINT pk_glossario_doencas PRIMARY KEY (id_glossario_doenca),
		CONSTRAINT fk_glossario_doencas_codprofissional FOREIGN KEY(cod_profissional_cadastro)
			REFERENCES profissionais(id_profissional)
	);

	-- Criação tabela de medicamentos
	CREATE TABLE medicamentos(
		id_medicamento BIGSERIAL NOT NULL,
		cod_pessoa_juridica BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		observacoes TEXT,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_medicamentos PRIMARY KEY(id_medicamento),
		CONSTRAINT fk_medicamentos_codpessoajuridica FOREIGN KEY(cod_pessoa_juridica)
			REFERENCES pessoas_juridicas(cod_pessoa)
	);

	-- Criação tabela de sintomas apresentados por pacientes
	CREATE TABLE sintomas_apresentados(
		id_sintoma_apresentado BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		observacoes TEXT,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_sintomas_apresentados PRIMARY KEY(id_sintoma_apresentado)
	);

	-- Criação tabela de sintomas apresentados pelo paciente
	CREATE TABLE prontuarios_sintomas_apresentados(
		id_prontuario_sintoma_apresentado BIGSERIAL NOT NULL,
		cod_prontuario BIGSERIAL NOT NULL,
		cod_sintoma_apresentado BIGSERIAL NOT NULL,
		dias_com_sintomas INT NOT NULL,

		CONSTRAINT pk_pront_sintomas_apresentados PRIMARY KEY(id_prontuario_sintoma_apresentado),
		CONSTRAINT fk_pront_sintomas_apresentados_codprontuario FOREIGN KEY(cod_prontuario)
			REFERENCES prontuarios(id_prontuario),
		CONSTRAINT fk_pront_sintomas_apresentados_codsintomaapresentado FOREIGN KEY(cod_sintoma_apresentado)
			REFERENCES sintomas_apresentados(id_sintoma_apresentado)
	);

	-- Criação tabela de medicamentos utilizados pelo paciente
	CREATE TABLE prontuarios_medicamentos(
		id_prontuario_medicamento BIGSERIAL NOT NULL,
		cod_prontuario BIGSERIAL NOT NULL,
		cod_medicamento BIGSERIAL NOT NULL,
		dias_com_tratamento INT NOT NULL,
		quantidade INT NOT NULL,
		parecer_profissional TEXT NOT NULL,

		CONSTRAINT pk_prontuarios_medicamentos PRIMARY KEY(id_prontuario_medicamento),
		CONSTRAINT fk_prontuarios_medicamentos_codprontuario FOREIGN KEY(cod_prontuario)
			REFERENCES prontuarios(id_prontuario),
		CONSTRAINT fk_prontuarios_medicamentos_codmedicamento FOREIGN KEY(cod_medicamento)
			REFERENCES medicamentos(id_medicamento)
	);

	-- Criação tabela de glossario de sintomas apresentados pelo paciente
	CREATE TABLE glossario_doencas_sintomas_apresentados(
		id_glossario_doenca_sintoma_apresentado BIGSERIAL NOT NULL,
		cod_glossario_doenca BIGSERIAL NOT NULL,
		cod_sintoma_apresentado BIGSERIAL NOT NULL,
		dias_com_sintomas INT NOT NULL,

		CONSTRAINT pk_g_doencas_sintomas_apresentados PRIMARY KEY(id_glossario_doenca_sintoma_apresentado),
		CONSTRAINT fk_g_doencas_sintomas_apresentados_codglossariodoenca FOREIGN KEY(cod_glossario_doenca)
			REFERENCES glossario_doencas(id_glossario_doenca),
		CONSTRAINT fk_g_doencas_sintomas_apresentados_codsintomaapresentado FOREIGN KEY(cod_sintoma_apresentado)
			REFERENCES sintomas_apresentados(id_sintoma_apresentado)
	);

	-- Criação tabela de glossario de medicamentos utilizados pelo paciente
	CREATE TABLE glossario_doenca_medicamentos(
		id_glossario_doenca_medicamento BIGSERIAL NOT NULL,
		cod_glossario_doenca BIGSERIAL NOT NULL,
		cod_medicamento BIGSERIAL NOT NULL,
		dias_com_tratamento INT NOT NULL,
		quantidade INT NOT NULL,
		parecer_profissional TEXT NOT NULL,

		CONSTRAINT pk_g_doencas_medicamentos PRIMARY KEY(id_glossario_doenca_medicamento),
		CONSTRAINT fk_g_doencas_medicamentos_codprontuario FOREIGN KEY(cod_glossario_doenca)
			REFERENCES glossario_doencas(id_glossario_doenca),
		CONSTRAINT fk_g_doencas_medicamentos_codmedicamento FOREIGN KEY(cod_medicamento)
			REFERENCES medicamentos(id_medicamento)
	);

	-- criação da tabela de motivos_agendas --
	CREATE TABLE motivos_agendas(
		id_motivo_agenda BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		observacoes TEXT,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_motivos_agendas PRIMARY KEY(id_motivo_agenda)
	);
	
	-- criação da tabela de agendas --
	CREATE TABLE agendas(
		id_agenda BIGSERIAL NOT NULL,
		cod_cliente BIGSERIAL NOT NULL,
		cod_funcionario BIGSERIAL NOT NULL,
		cod_profissional BIGSERIAL NOT NULL,
		cod_motivo_agenda BIGSERIAL NOT NULL,
		data_agendamento DATE NOT NULL,
		horario DECIMAL(20,15) NOT NULL DEFAULT 0.0,
		observacoes TEXT,
		
		CONSTRAINT pk_agendas PRIMARY KEY(id_agenda),
		CONSTRAINT fk_agendas_codcliente FOREIGN KEY(cod_cliente) REFERENCES clientes(id_cliente),
		CONSTRAINT fk_agendas_codfuncionario FOREIGN KEY(cod_funcionario) REFERENCES funcionarios(id_funcionario),
		CONSTRAINT fk_agendas_codprofissional FOREIGN KEY(cod_profissional) REFERENCES profissionais(id_profissional),
		CONSTRAINT fk_agendas_codmotivo FOREIGN KEY(cod_motivo_agenda) REFERENCES motivos_agendas(id_motivo_agenda),
		CONSTRAINT chk_data_agendamento CHECK(data_agendamento >= NOW())
	);
	
	-- criação da tabela de tipos de exames --
	CREATE TABLE tipos_exames(
		id_tipo_exame BIGSERIAL NOT NULL,
		descricao VARCHAR(100) NOT NULL,
		observacoes TEXT,
		ativo BOOLEAN NOT NULL DEFAULT 't',

		CONSTRAINT pk_tipos_exames PRIMARY KEY(id_tipo_exame)
	);

	-- criação da tabela de agendas --
	CREATE TABLE exames(
		id_exame BIGSERIAL NOT NULL,
		cod_tipo_exame BIGSERIAL NOT NULL,
		cod_cliente BIGSERIAL NOT NULL,
		cod_pessoa_juridica BIGSERIAL NOT NULL,
		data_agendamento DATE NOT NULL,
		laudo TEXT NOT NULL,
		obsetvacoes TEXT,
		
		CONSTRAINT pk_exames PRIMARY KEY(id_exame),
		CONSTRAINT fk_exames_codtipoexame FOREIGN KEY(cod_tipo_exame) REFERENCES exames(id_exame),
		CONSTRAINT fk_exames_codcliente FOREIGN KEY(cod_cliente) REFERENCES clientes(id_cliente),
		CONSTRAINT fk_agendas__codpessoajuridica FOREIGN KEY(cod_pessoa_juridica)
			REFERENCES pessoas_juridicas(cod_pessoa),
		CONSTRAINT chk_data_demicao CHECK(data_agendamento <= NOW())
	);
	
	-- Criação tabela auditorias
	CREATE TABLE auditorias(
		id_data_hora_acao TIMESTAMP WITH TIME ZONE NOT NULL,
		id_sequencia BIGSERIAL NOT NULL,
		id_cod_usuario BIGSERIAL NOT NULL,
		id_cod_acao BIGSERIAL NOT NULL,
		comando_utilizado TEXT NOT NULL,

		CONSTRAINT pk_auditorias PRIMARY KEY(id_data_hora_acao, id_sequencia, id_cod_usuario, id_cod_acao)
	);
--
--DROP TABLE auditorias;
--

	--DROP TABLE exames;
	--DROP TABLE tipos_exames;
	--DROP TABLE agendas;
	--DROP TABLE motivos_agendas;
	--DROP TABLE glossario_doenca_medicamentos;
	--DROP TABLE glossario_doencas_sintomas_apresentados;
	--DROP TABLE prontuarios_medicamentos;
	--DROP TABLE prontuarios_sintomas_apresentados;
	--DROP TABLE sintomas_apresentados;
	--DROP TABLE medicamentos;
	--DROP TABLE glossario_doencas;
	--DROP TABLE profissionais_planos_saude;
	--DROP TABLE clientes_tipos_planos;
	--DROP TABLE tipos_planos;
	--DROP TABLE planos_saude;
	--DROP TABLE prontuarios;
	--DROP TABLE clientes;
	--DROP TABLE situacoes_clientes;
	--DROP TABLE pessoas_especializacoes;
	--DROP TABLE profissionais_turnos;
	--DROP TABLE profissionais;
	--DROP TABLE especializacoes;
	--DROP TABLE acoes_usuarios;
	--DROP TABLE usuarios;
	--DROP TABLE acoes;
	--DROP TABLE funcionarios;
	--DROP TABLE funcoes;
	--DROP TABLE situacoes_funcionarios;
	--DROP TABLE telefones;
	--DROP TABLE tipos_telefones;
	--DROP TABLE enderecos;
	--DROP TABLE tipos_enderecos;
	--DROP TABLE bairros;
	--DROP TABLE cidades;
	--DROP TABLE estados;
	--DROP TABLE paises;
	--DROP TABLE pessoas_juridicas;
	--DROP TABLE pessoas_fisicas;
	--DROP TABLE pessoas;
	--DROP TABLE situacoes_pessoas;


-- Função para excluir registro sem que seja preciso uma restrição de cascade nas tabelas do banco
-- Função obtida em: http://stackoverflow.com/questions/129265/cascade-delete-just-once em 25/04/2017
-- Exemplo de utilização: SELECT delete_cascade('public','my_table','1');
CREATE OR REPLACE FUNCTION delete_cascade(p_schema VARCHAR, p_table VARCHAR, p_key VARCHAR, p_recursion VARCHAR[] DEFAULT NULL)
RETURNS INTEGER AS $$
	DECLARE
		rx RECORD;
		rd RECORD;
		v_sql VARCHAR;
		v_recursion_key VARCHAR;
		recnum INTEGER;
		v_primary_key VARCHAR;
		v_rows INTEGER;
	BEGIN
		recnum := 0;
		SELECT ccu.column_name INTO v_primary_key
		  FROM information_schema.table_constraints  tc
			JOIN information_schema.constraint_column_usage AS ccu 
			  ON ccu.constraint_name = tc.constraint_name 
				AND ccu.constraint_schema=tc.constraint_schema
				AND tc.constraint_type='PRIMARY KEY'
				AND tc.table_name=p_table
				AND tc.table_schema=p_schema;

		FOR rx IN (SELECT kcu.table_name AS foreign_table_name, 
						  kcu.column_name AS foreign_column_name, 
						  kcu.table_schema foreign_table_schema,
						  kcu2.column_name AS foreign_table_primary_key
					 FROM information_schema.constraint_column_usage ccu
						JOIN information_schema.table_constraints tc ON tc.constraint_name=ccu.constraint_name AND tc.constraint_catalog=ccu.constraint_catalog AND ccu.constraint_schema=ccu.constraint_schema 
						JOIN information_schema.key_column_usage kcu ON kcu.constraint_name=ccu.constraint_name AND kcu.constraint_catalog=ccu.constraint_catalog AND kcu.constraint_schema=ccu.constraint_schema
						JOIN information_schema.table_constraints tc2 ON tc2.table_name=kcu.table_name AND tc2.table_schema=kcu.table_schema
						JOIN information_schema.key_column_usage kcu2 ON kcu2.constraint_name=tc2.constraint_name AND kcu2.constraint_catalog=tc2.constraint_catalog AND kcu2.constraint_schema=tc2.constraint_schema
					WHERE ccu.table_name=p_table
					  AND ccu.table_schema=p_schema
					  AND TC.CONSTRAINT_TYPE='FOREIGN KEY'
					  AND tc2.constraint_type='PRIMARY KEY'
		)
		LOOP
		v_sql := 'SELECT '||rx.foreign_table_primary_key||' AS key 
		            FROM '||rx.foreign_table_schema||'.'||rx.foreign_table_name||'
				   WHERE '||rx.foreign_column_name||'='||quote_literal(p_key)||' 
				     FOR UPDATE';
		--raise notice '%',v_sql;
		--found a foreign key, now find the primary keys for any data that exists in any of those tables.
			FOR rd IN EXECUTE v_sql
			LOOP
				v_recursion_key=rx.foreign_table_schema||'.'||rx.foreign_table_name||'.'||rx.foreign_column_name||'='||rd.key;
				IF (v_recursion_key = ANY (p_recursion)) THEN
					--raise notice 'Avoiding infinite loop';
				ELSE
					--raise notice 'Recursing to %,%',rx.foreign_table_name, rd.key;
					recnum:= recnum +delete_cascade(rx.foreign_table_schema::VARCHAR, rx.foreign_table_name::VARCHAR, rd.key::VARCHAR, p_recursion||v_recursion_key);
				END IF;
			END LOOP;
		END LOOP;
		BEGIN
			--actually delete original record.
			v_sql := 'delete from '||p_schema||'.'||p_table||' where '||v_primary_key||'='||quote_literal(p_key);
			EXECUTE v_sql;
			GET DIAGNOSTICS v_rows= row_count;
			--raise notice 'Deleting %.% %=%',p_schema,p_table,v_primary_key,p_key;
			recnum:= recnum +v_rows;
			EXCEPTION WHEN OTHERS THEN recnum=0;
		END;

		RETURN recnum;
	END;
$$ LANGUAGE PLPGSQL;


-- Incluindo as ações na tabela de "acoes"
INSERT INTO acoes (
	SELECT (
		SELECT nr_ordem
		  FROM (SELECT * FROM pg_tables WHERE schemaname = 'public') As oldtable
		    CROSS JOIN (SELECT ARRAY(SELECT tablename FROM pg_tables WHERE schemaname = 'public') As id_join) AS oldids
		      CROSS JOIN generate_series(1, (SELECT COUNT(*) FROM pg_tables WHERE schemaname = 'public')) AS nr_ordem
		 WHERE oldids.id_join[nr_ordem] = oldtable.tablename AND oldtable.tablename = pg_tables.tablename) AS ordem, tablename 
	  FROM pg_tables 
	 WHERE schemaname = 'public' 
	 ORDER BY ordem
);
-- Limpando a tabelade "acoes_usuarios"
DELETE FROM acoes_usuarios;


-- Para cada usuário novo deve ser executada o insert a seguir que inclui ações para usuários na tabelade "acoes_usuarios".
-- Para isso foi criada uma trigger que é ativada depois de uma nova inserção em "usuarios". 
CREATE OR REPLACE FUNCTION insAcoesUsuarios ()
RETURNS TRIGGER AS $new_usuario$
	BEGIN
		IF ( NEW.administrador ) THEN 
			INSERT INTO acoes_usuarios (
				SELECT ((SELECT COUNT(*) FROM acoes_usuarios) + (
					SELECT nr_ordem
					  FROM (SELECT * FROM acoes) As oldtable
						CROSS JOIN (SELECT ARRAY(SELECT id_acao FROM acoes) As id_join) AS oldids
						  CROSS JOIN generate_series(1, (SELECT COUNT(*) FROM acoes)) AS nr_ordem
					 WHERE oldids.id_join[nr_ordem] = oldtable.id_acao AND oldtable.id_acao = acoes.id_acao) + (SELECT COUNT(*) FROM acoes)) AS id_acao_usuario, id_acao, NEW.id_usuario, 't', 't', 't', 't' 
				  FROM acoes
			 	 ORDER BY id_acao
			);
		ELSE
			INSERT INTO acoes_usuarios (
				SELECT ((SELECT COUNT(*) FROM acoes_usuarios) + (
					SELECT nr_ordem
					  FROM (SELECT * FROM acoes) As oldtable
						CROSS JOIN (SELECT ARRAY(SELECT id_acao FROM acoes) As id_join) AS oldids
						  CROSS JOIN generate_series(1, (SELECT COUNT(*) FROM acoes)) AS nr_ordem
					 WHERE oldids.id_join[nr_ordem] = oldtable.id_acao AND oldtable.id_acao = acoes.id_acao) + (SELECT COUNT(*) FROM acoes)) AS id_acao_usuario, id_acao, NEW.id_usuario, 'f', 'f', 'f', 't' 
				  FROM acoes
				 ORDER BY id_acao
			);
		END IF;
			
		RETURN NEW;
	END;
$new_usuario$ LANGUAGE plpgsql; 
--
-- DROP gatilho: DROP TRIGGER insert_usuario ON usuarios; 
--
CREATE TRIGGER insert_usuario  AFTER INSERT ON usuarios FOR EACH ROW EXECUTE PROCEDURE insAcoesUsuarios();


-- Para cada ação nova deve ser executada o insert a seguir que inclui a ações para usuários na tabelade "acoes_usuarios".
-- Para isso foi criada uma trigger que é ativada depois de uma nova inserção em "acoes". 
CREATE OR REPLACE FUNCTION insAcoesUsuarios_2()
RETURNS TRIGGER AS $new_acoes$
	DECLARE
		r record;
	BEGIN
		FOR r IN SELECT * FROM usuarios ORDER BY id_usuario LOOP
			IF r.administrador = 't' THEN
				INSERT INTO acoes_usuarios VALUES ((SELECT MAX(id_acao_usuario) FROM acoes_usuarios) + 1, NEW.id_acao,r.id_usuario, 't', 't', 't', 't');
			ELSE
				INSERT INTO acoes_usuarios VALUES ((SELECT MAX(id_acao_usuario) FROM acoes_usuarios) + 1, NEW.id_acao,r.id_usuario, 'f', 'f', 'f', 't');
			END IF;
		END LOOP;
			
		RETURN NEW;
	END;
$new_acoes$ LANGUAGE plpgsql; 
--
-- DROP gatilho: DROP TRIGGER insert_acao ON acoes; 
--
CREATE TRIGGER insert_acao  AFTER INSERT ON acoes FOR EACH ROW EXECUTE PROCEDURE insAcoesUsuarios_2();


-- incluir usuário ADMINISTRADOR "ZERO"
INSERT INTO situacoes_funcionarios(id_situacao,descricao,observacoes,ativo) VALUES (0,'ADMINISTRADOR','ADMINISTRADOR','t');
INSERT INTO situacoes_pessoas(id_situacao,descricao,observacoes,ativo) VALUES (0,'ADMINISTRADOR','ADMINISTRADOR','t');
INSERT INTO pessoas(id_pessoa,cod_situacao,nome,sobrenome,observacoes) VALUES (0,0,'ADMINISTRADOR','ADMINISTRADOR','ADMINISTRADOR');
INSERT INTO funcoes(id_funcao,descricao) VALUES (0,'ADMINISTRADOR');
INSERT INTO funcionarios(id_funcionario,cod_situacao,cod_pessoa,cod_funcao,vlr_salario,dias_trabalhados_mes,carga_horaria,data_admissao,data_demissao,numero_clt) VALUES (0,0,0,0,0.00,0,0,'2017-04-06',NULL,0);
INSERT INTO usuarios(id_usuario,cod_funcionario,login,senha,administrador) VALUES (0,0,'ADMINISTRADOR','ADMIN','t');




-- Crasção de VIEWs para relatórios
-- View para pessoas --
CREATE 
  VIEW pessoas_fisicas_view(id_pessoa,nomecompleto,cpf,rg,nome_mae,data_nascimento,sexo,situacao,observacao) 
	AS 
	 SELECT pessoas.id_pessoa,pessoas.nome||' '||pessoas.sobrenome,pessoas_fisicas.cpf,pessoas_fisicas.rg,pessoas_fisicas.nome_mae,
			pessoas_fisicas.data_nascimento,pessoas_fisicas.sexo,situacoes_pessoas.descricao,pessoas.observacoes 
	   FROM ((pessoas_fisicas 
			INNER JOIN pessoas ON(pessoas.id_pessoa = pessoas_fisicas.cod_pessoa))
				INNER JOIN situacoes_pessoas ON(situacoes_pessoas.id_situacao = pessoas.cod_situacao));
	
-- View para enderecos de Pessoas Físicas --
CREATE 
  VIEW enderecos_pf_view(id_endereco,cod_pessoa,tipo_endereco,descricao,bairro,cidade,estado,pais,cep,complemento,ativo) 
	AS 
	 SELECT enderecos.id_endereco,enderecos.cod_pessoa,tipos_enderecos.descricao,enderecos.descricao,enderecos.complemento,enderecos.cep,bairros.descricao,
		cidades.codigo_ibge||'/'||cidades.descricao,estados.codigo_ibge||'/'||estados.descricao,paises.codigo_ibge||'/'||paises.descricao,enderecos.ativo
	   FROM (((((enderecos 
			INNER JOIN tipos_enderecos ON(tipos_enderecos.id_tipo_endereco = enderecos.cod_tipo_endereco))
				INNER JOIN bairros ON(bairros.id_bairro = enderecos.cod_bairro))
					INNER JOIN cidades ON(cidades.id_cidade = bairros.cod_cidade))
						INNER JOIN estados ON(estados.id_estado = cidades.cod_estado))
							INNER JOIN paises ON(paises.id_pais = estados.cod_pais));
	
-- View para telefones Pessoa Física --
CREATE 
  VIEW telefones_pf_view(id_telefone,cod_pessoa,tipo_telefone,numero,ramal,contato,ativo) 
	AS 
	 SELECT telefones.id_telefone,telefones.cod_pessoa,tipos_telefones.descricao,telefones.numero,telefones.ramal,telefones.contato,telefones.ativo
	   FROM telefones INNER JOIN tipos_telefones ON(tipos_telefones.id_tipo_telefone = telefones.cod_tipo_telefone);

-- View para agendas --
CREATE 
  VIEW agendas_view(id_agenda,cod_cliente,descr_cliente,cod_funcionario,descr_funcionario,cod_profissional,
		descr_profissional,cod_motivo_agenda,descr_motivo_agenda,cod_plano_saude,descr_plano_saude,
		cod_tipo_plano_saude,descr_tipo_plano_saude,data_agendamento,horario,observacoes) 
	AS 
	SELECT agendas.id_agenda,
	   agendas.cod_cliente,
		(SELECT pessoas.nome ||' '|| pessoas.sobrenome 
		  FROM (pessoas 
			INNER JOIN clientes 
			   ON(pessoas.id_pessoa = clientes.cod_pessoa))
			   INNER JOIN agendas _agendas ON(clientes.id_cliente = _agendas.cod_cliente)
		  WHERE _agendas.id_agenda = agendas.id_agenda) AS descr_cliente,
       agendas.cod_funcionario,
		(SELECT pessoas.nome ||' '|| pessoas.sobrenome 
		  FROM (pessoas 
			INNER JOIN funcionarios 
			   ON(pessoas.id_pessoa = funcionarios.cod_pessoa))
			   INNER JOIN agendas _agendas ON(funcionarios.id_funcionario = _agendas.cod_funcionario)
		  WHERE _agendas.id_agenda = agendas.id_agenda) AS descr_funcionario,
       agendas.cod_profissional,
		(SELECT pessoas.nome ||' '|| pessoas.sobrenome 
		  FROM (pessoas 
			INNER JOIN profissionais 
			   ON(pessoas.id_pessoa = profissionais.cod_pessoa))
			   INNER JOIN agendas _agendas ON(profissionais.id_profissional = _agendas.cod_profissional)
		  WHERE _agendas.id_agenda = agendas.id_agenda) AS descr_profissional,
       agendas.cod_motivo_agenda,
		(SELECT motivos_agendas.descricao 
		  FROM (motivos_agendas 
			INNER JOIN agendas _agendas
			   ON(motivos_agendas.id_motivo_agenda = _agendas.cod_motivo_agenda))
		  WHERE _agendas.id_agenda = agendas.id_agenda) AS descr_motivo_agenda,
		-- código do plano de saúde
		(SELECT planos_saude.id_plano_saude
		  FROM ((((planos_saude 
			INNER JOIN tipos_planos 
			   ON(planos_saude.id_plano_saude = tipos_planos.cod_plano_saude))
			   INNER JOIN clientes_tipos_planos
				  ON (tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano))
				  LEFT JOIN clientes
				    ON(tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano
					  AND clientes.id_cliente = clientes_tipos_planos.cod_cliente))
				    INNER JOIN agendas _agendas
					   ON(clientes.id_cliente = _agendas.cod_cliente))
		  WHERE _agendas.id_agenda = agendas.id_agenda) AS cod_plano_saude,
		-- descrição do plano de saúde
		(SELECT planos_saude.descricao
		  FROM ((((planos_saude 
			INNER JOIN tipos_planos 
			   ON(planos_saude.id_plano_saude = tipos_planos.cod_plano_saude))
			   INNER JOIN clientes_tipos_planos
				  ON (tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano))
				  LEFT JOIN clientes
				    ON(tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano
					  AND clientes.id_cliente = clientes_tipos_planos.cod_cliente))
				    INNER JOIN agendas _agendas
					   ON(clientes.id_cliente = _agendas.cod_cliente))
		  WHERE _agendas.id_agenda = agendas.id_agenda) AS descr_plano_saude,
		-- código do tipo de plano de saúde
		(SELECT tipos_planos.id_tipo_plano
		  FROM ((((tipos_planos 
			INNER JOIN planos_saude 
			   ON(planos_saude.id_plano_saude = tipos_planos.cod_plano_saude))
			   INNER JOIN clientes_tipos_planos
				  ON (tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano))
				  LEFT JOIN clientes
				    ON(tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano
					  AND clientes.id_cliente = clientes_tipos_planos.cod_cliente))
				    INNER JOIN agendas _agendas
					   ON(clientes.id_cliente = _agendas.cod_cliente))
		  WHERE _agendas.id_agenda = agendas.id_agenda) AS cod_tipo_plano_saude,
		-- descrição do tipo de plano de saúde
		(SELECT tipos_planos.descricao
		  FROM ((((tipos_planos 
			INNER JOIN planos_saude 
			   ON(planos_saude.id_plano_saude = tipos_planos.cod_plano_saude))
			   INNER JOIN clientes_tipos_planos
				  ON (tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano))
				  LEFT JOIN clientes
				    ON(tipos_planos.id_tipo_plano = clientes_tipos_planos.cod_tipo_plano
					  AND clientes.id_cliente = clientes_tipos_planos.cod_cliente))
				    INNER JOIN agendas _agendas
					   ON(clientes.id_cliente = _agendas.cod_cliente))
		  WHERE _agendas.id_agenda = agendas.id_agenda) AS descr_tipo_plano_saude,
	   agendas.data_agendamento,
	   agendas.horario,
	   agendas.observacoes
  FROM agendas
 ORDER BY agendas.id_agenda;




------------------------------------------------------------------------------
-- Fim do arquivo --
