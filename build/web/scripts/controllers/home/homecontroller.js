/*
 Author: Leandro Correa
 Github: leandro8d
 linkedin/email: leandro8d@hotmail.com
 */
app.controller("homeController", function ($scope, $parse, $http, $uibModal) {


    $scope.condicoes;
    $http({
        method: 'GET',
        url: 'api/servicoSX/getCondicoes',
    }).then(function successCallback(response) {
        $scope.condicoes = response.data;
    }, function errorCallback(response) {
        console.log(response);
    }
    );

    $http({
        method: 'GET',
        url: 'api/servicoSX/getTiposProblema',
    }).then(function successCallback(response) {
        $scope.problemas = response.data;
    }, function errorCallback(response) {
        console.log(response);
    }
    );

    $scope.open = function (titulo, mensagem, pergunta, caseYes, caseNo) {
        var returnedvalue;
        var modalInstance = $uibModal.open({
            templateUrl: pergunta ? './commoms/html/modalPergunta.html' : './commoms/html/modalErro.html',
            controller: 'modalsController',
            resolve: {titulo: function () {
                    return titulo;
                }, mensagem: function () {
                    return mensagem;
                },
                pergunta: function () {
                    return pergunta;
                }}
        });
        modalInstance.result.then(function (result) {
            if (pergunta) {
                if (result) {
                    if (caseYes)
                        caseYes();
                } else
                if (caseNo)
                    caseNo();
            }
        });
        return returnedvalue;
    };






    $scope.rows = [];
    $scope.rowheader = [];
    $scope.valor;
    $scope.columnsTest = [];
    $scope.selectedCondicao = "";
    $scope.selectedRow;
    $scope.final = 0;
    $scope.flagFObjApagada = false;
    $scope.resultado = [];

    $scope.setSelected = function (item) {
        $scope.selectedRow = item;
    }

    // Function that trigger the calculation to be made according to the selected item from dropdown

    $scope.addColumn = function () {
        var j = 0;
        if ($scope.formSimplex.$valid) {
            if ($scope.funcao.indexOf(",") == -1) {
                var arrayFuncao = $scope.funcao.split('+');

                if (($scope.rows.length != 0 && $scope.rows[0].length == (arrayFuncao.length + 2)) || $scope.rows.length == 0) {


                    var columns = [];

                    angular.forEach(arrayFuncao, function (valor, indice) {
                        if ($scope.rows.length == 0) {
                            $scope.rowheader.push('Var' + (j++));
                        }
                        columns.push(valor);
                    });
                    if ($scope.rows.length == 0) {
                        $scope.rowheader.push('Condicao');
                        $scope.rowheader.push('M. Livre');
                    }
                    if ($scope.selectedCondicao == null || $scope.selectedCondicao == "") {
                        columns.push("=");
                    } else {
                        columns.push($scope.selectedCondicao);
                    }
                    columns.push($scope.final);
                    if ($scope.flagFObjApagada) {
                        $scope.rows.unshift(columns);
                        $scope.flagFObjApagada = false;
                    } else {
                        $scope.rows.push(columns);
                    }
                } else {
                    $scope.open("Erro", "A quantidade de variáveis precisam ser coerentes com a quantidade de variáveis que ja foram inseridas, ou apenas uma variável.", false);

                }
            }
                else
                {
                    $scope.open("Erro", "O separador de casas decimais precisa ser '.' ao invez de vírgula!", false);

                }
            } else
                $scope.open("Erro", "Preecha todos os campos antes de inserir uma linha!", false);
    };


    $scope.calcular = function ()
    {
        $scope.open("Alerta", "Você realmente deseja realizar o cálculo?", true, function () {
            if ($scope.rows.length >= 2) {
                $scope.condicoes;
                $http({
                    method: 'POST',
                    url: 'api/servicoSX/calcularValores',
                    data: {vetor: $scope.rows, problema: $scope.selectedProblema},
                }).then(function successCallback(response) {
                    var result = response.data;
                    if (result.error) {
                        $scope.open("Erro", result.errorMessage, false);
                    } else
                        $scope.resultado = result.data;
                }, function errorCallback(response) {
                    console.log(response);
                }
                );
            } else
            {
                $scope.open("Erro", "Para realizar o cálculo é necessário inserir a função objetiva e ao menos uma restrição.", true);

            }
        });
    };

    $scope.recarregar = function () {
        $scope.open("Aviso", "Você realmente deseja recarregar todos os dados da tela?", true,
                function () {
                    $scope.rows = [];
                    $scope.rowheader = [];
                    $scope.valor;
                    $scope.columnsTest = [];
                    $scope.selectedCondicao = "";
                    $scope.selectedRow;
                    $scope.final = 0;
                    $scope.funcao = "";
                    $scope.resultado = [];
                }
        );

    };






    $scope.getCellValue = function (row, column) {

        var getter = $parse(column.value);
        return getter(row);

        // Alternatively:
        // return $parse(column.value)(row);
    };




    $scope.apagarLinha = function () {
        $scope.open("Aviso", "Deseja realmente apagar a linha selecionada?", true, function () {
            if ($scope.selectedRow != null) {
                if (!$scope.flagFObjApagada) {
                    if ($scope.selectedRow == $scope.rows[0]) {
                        $scope.flagFObjApagada = true;
                        $scope.selectedCondicao = null;
                        $scope.final = 0;
                    }
                    $scope.rows.splice($scope.rows.indexOf($scope.selectedRow), 1);
                } else
                {
                    $scope.open("Erro", "Insira a função objetivo antes de apagar outro item!", false);
                }
                $scope.selectedRow = null;
            } else
                $scope.open("Erro", "Selecione uma linha antes de apagar!", false);
        });

    };

    $scope.open("Dica", "\
O campo função deve seguir o padrão: 2+3+-5+7...., onde cada valor representa cada variavel. O primeiro item a ser inserido sempre será a função objetivo. O seprador \n\
de casas decimais é o '.' e não a vírgula.", false, null, null);

});