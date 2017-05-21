/* 
 * Trabalho Simplex
 * Autores:Leandro;Jonathan;Yuri  * 
 */

app.controller("modalsController", function ($scope, $uibModalInstance,titulo,mensagem) {
    
    $scope.titulo = titulo;
    $scope.mensagem = mensagem;
    $scope.ok = function (value) {
        if(value)
    $uibModalInstance.close(value);
else
    $uibModalInstance.close(value);
  };
  
   

});

