document.addEventListener("DOMContentLoaded", function () {

    const formBusca = document.getElementById("formBusca");
    const tipoUsuarioSelect = document.getElementById("tipoUsuario");
    const cidadeInput = document.getElementById("cidade");
    const tabelaUsuarios = document.getElementById("tabelaUsuarios");

    if (formBusca && tipoUsuarioSelect && cidadeInput && tabelaUsuarios) {
        formBusca.addEventListener("change", function () {
            const tipoUsuario = tipoUsuarioSelect.value;
            const cidade = cidadeInput.value.toLowerCase();

            const usuarios = [...document.querySelectorAll("#tabelaUsuarios tbody tr")];
            usuarios.forEach(function (usuario) {
                const tipoUsuarioTd = usuario.querySelector("td:nth-child(1)").textContent.toLowerCase();
                const cidadeTd = usuario.querySelector("td:nth-child(3)").textContent.toLowerCase();

                const tipoUsuarioMatch = tipoUsuario === "" || tipoUsuarioTd.includes(tipoUsuario.toLowerCase());
                const cidadeMatch = cidade === "" || cidadeTd.includes(cidade);

                if (tipoUsuarioMatch && cidadeMatch) {
                    usuario.style.display = "";
                } else {
                    usuario.style.display = "none";
                }
            });
        });

        cidadeInput.addEventListener("input", function () {
            formBusca.dispatchEvent(new Event('change'));
        });
    }

    function validarCPF(cpf) {
        const regexCPF = /^\d{3}\.\d{3}\.\d{3}-\d{2}$|^\d{11}$/;
        return regexCPF.test(cpf);
    }

    function validarTelefone(telefone) {
        const regexTelefone = /^\(\d{2}\)\s\d{5}-\d{4}$/;
        return regexTelefone.test(telefone);
    }

    function validarCEP(cep) {
        const regexCEP = /^\d{5}-\d{3}$/;
        return regexCEP.test(cep);
    }

    function validarDataNascimento(data) {
        const regexData = /^\d{2}\/\d{2}\/\d{4}$/;
        return regexData.test(data);
    }

    function validarEmail(email) {
        const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regexEmail.test(email);
    }

    function validarEstado(estado) {
        const regexLetras = /^[A-Za-z]+$/;
        return regexLetras.test(estado);
    }

    function adicionarFormatacaoAutomatica() {
        const campoCPF = document.getElementById("cpf");
        const campoTelefone = document.getElementById("telefone");
        const campoCEP = document.getElementById("cep");
        const campoDataNascimento = document.getElementById("dataNascimento");
        const campoEstado = document.getElementById("estado");

        if (campoCPF) {
            campoCPF.addEventListener("input", function () {
                let valor = campoCPF.value.replace(/\D/g, "");
                if (valor.length > 11) {
                    valor = valor.slice(0, 11);
                }
                if (valor.length > 3) {
                    valor = valor.replace(/^(\d{3})(\d)/, "$1.$2");
                }
                if (valor.length > 7) {
                    valor = valor.replace(/^(\d{3})\.(\d{3})(\d)/, "$1.$2.$3");
                }
                if (valor.length > 10) {
                    valor = valor.replace(/\.(\d{3})(\d)/, ".$1-$2");
                }
                campoCPF.value = valor;
            });
        }

        if (campoTelefone) {
            campoTelefone.addEventListener("input", function () {
                let valor = campoTelefone.value.replace(/\D/g, "");
                if (valor.length > 11) {
                    valor = valor.slice(0, 11);
                }
                if (valor.length > 0) {
                    valor = "(" + valor;
                }
                if (valor.length > 3) {
                    valor = valor.slice(0, 3) + ") " + valor.slice(3);
                }
                if (valor.length > 10) {
                    valor = valor.slice(0, 10) + "-" + valor.slice(10);
                }
                campoTelefone.value = valor;
            });
        }

        if (campoCEP) {
            campoCEP.addEventListener("input", function () {
                let valor = campoCEP.value.replace(/\D/g, "");
                if (valor.length > 8) {
                    valor = valor.slice(0, 8);
                }
                if (valor.length > 5) {
                    valor = valor.replace(/^(\d{5})(\d)/, "$1-$2");
                }
                campoCEP.value = valor;
            });
        }

        if (campoDataNascimento) {
            campoDataNascimento.addEventListener("input", function () {
                let valor = campoDataNascimento.value.replace(/\D/g, "");
                if (valor.length > 8) {
                    valor = valor.slice(0, 8);
                }
                if (valor.length > 2) {
                    valor = valor.replace(/^(\d{2})(\d)/, "$1/$2");
                }
                if (valor.length > 5) {
                    valor = valor.replace(/^(\d{2})\/(\d{2})(\d)/, "$1/$2/$3");
                }
                campoDataNascimento.value = valor;
            });
        }

        if (campoEstado) {
            campoEstado.addEventListener("input", function () {
                let valor = campoEstado.value.replace(/[^A-Za-z]/g, "");
                valor = valor.slice(0, 2);
                campoEstado.value = valor.toUpperCase();
            });
        }
    }

    function adicionarListenersValidacao() {
        const campos = {
            'nome': 'Nome é obrigatório.',
            'sobrenome': 'Sobrenome é obrigatório.',
            'tipoUsuario': 'Tipo de Usuário é obrigatório.',
            'dataNascimento': 'Data de Nascimento é obrigatória.',
            'cpf': 'Número de CPF é obrigatório.',
            'telefone': 'Telefone é obrigatório.',
            'cep': 'CEP é obrigatório.',
            'rua': 'Rua é obrigatória.',
            'numero': 'Número é obrigatório.',
            'complemento': '',
            'bairro': 'Bairro é obrigatório.',
            'cidade': 'Cidade é obrigatória.',
            'estado': 'Estado é obrigatório e deve conter apenas letras.',
            'email': 'Email é obrigatório.',
            'senha': 'Senha é obrigatória.'
        };

        for (const campoId in campos) {
            if (campoId !== 'cnpj') {
                const campo = document.getElementById(campoId);
                if (campo) {
                    campo.addEventListener("blur", function () {
                        const valor = campo.value.trim();
                        if (valor === "") {
                            alert("Campo obrigatório: " + campos[campoId]);
                            campo.style.borderColor = "red";
                            campo.style.borderWidth = "2px"
                        } else {
                            switch (campoId) {
                                case 'cpf':
                                    if (!validarCPF(valor)) {
                                        alert("CPF inválido. Use o formato XXX.XXX.XXX-XX ou XXXXXXXXXXX");
                                        campo.style.borderColor = "red";
                                        campo.style.borderWidth = "2px";
                                    } else {
                                        campo.style.borderColor = "";
                                        campo.style.borderWidth = "";
                                    }
                                    break;
                                case 'telefone':
                                    if (!validarTelefone(valor)) {
                                        alert("Telefone inválido. Use o formato (XX) XXXXX-XXXX");
                                        campo.style.borderColor = "red";
                                        campo.style.borderWidth = "2px";
                                    } else {
                                        campo.style.borderColor = "";
                                        campo.style.borderWidth = "";
                                    }
                                    break;
                                case 'cep':
                                    if (!validarCEP(valor)) {
                                        alert("CEP inválido. Use o formato XXXXX-XXX");
                                        campo.style.borderColor = "red";
                                        campo.style.borderWidth = "2px";
                                    } else {
                                        campo.style.borderColor = "";
                                        campo.style.borderWidth = "";
                                    }
                                    break;
                                case 'dataNascimento':
                                    if (!validarDataNascimento(valor)) {
                                        alert("Data de Nascimento inválida. Use o formato dd/mm/aaaa.");
                                        campo.style.borderColor = "red";
                                        campo.style.borderWidth = "2px";
                                    } else {
                                        campo.style.borderColor = "";
                                        campo.style.borderWidth = "";
                                    }
                                    break;
                                case 'email':
                                    if (!validarEmail(valor)) {
                                        alert("Email inválido. Insira um endereço de email válido.");
                                        campo.style.borderColor = "red";
                                        campo.style.borderWidth = "2px";
                                    } else {
                                        campo.style.borderColor = "";
                                        campo.style.borderWidth = "";
                                    }
                                    break;
                                case 'estado':
                                    if (!validarEstado(valor)) {
                                        alert("Estado inválido. Insira um estado válido com apenas letras.");
                                        campo.style.borderColor = "red";
                                        campo.style.borderWidth = "2px";
                                    } else {
                                        campo.style.borderColor = "";
                                        campo.style.borderWidth = "";
                                    }
                                    break;
                                default:
                                    campo.style.borderColor = "";
                                    campo.style.borderWidth = "";
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    adicionarFormatacaoAutomatica();
    adicionarListenersValidacao();
});
