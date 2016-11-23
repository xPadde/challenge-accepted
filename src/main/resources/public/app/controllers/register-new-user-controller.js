app.controller('RegisterNewUserController', ['$scope', '$log', 'userService', '$location',
    function ($scope, $log, userService, $location) {

        $scope.getUserInputsFromRegisterNewUserForm = function () {
            console.log("get user inputs");
            return JSON.stringify({
                'firstName': $('#input-first-name').val(),
                'lastName': $('#input-last-name').val(),
                'password': $('#input-password').val(),
                'email': $('#input-email').val(),
                'yubiKeyModel': {
                    'yubiKeyId': $('#input-otp').val().substring(0, 12)
                }
            })
        };

        $scope.registerNewUser = function (form) {
            if (form.validate()) {
                $scope.loggedInUser = JSON.parse(sessionStorage.getItem('loggedInUser'));
                userService.createNewUser($scope.getUserInputsFromRegisterNewUserForm())
                    .success(function (response, status) {
                        if (status == 201) {
                            $log.info('userService.createNewUser() called. New user created and saved to the database!');
                            $location.path('/home');
                        } else {
                            $log.info('userService.registerNewUser() called. User ***NOT*** created. Fields was empty!');
                        }
                    })
                    .error(function (response) {
                        $log.error('userService.registerNewUser() called. ***FAILED*** to create new user!');
                        $log.error(response);
                    });

                $('#form-register-new-user').each(function () {
                    this.reset();
                });
            }
        };

    }]);