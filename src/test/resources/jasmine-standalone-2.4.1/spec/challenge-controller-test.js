describe("ChallengeController", function () {

    beforeEach(module('ChallengeAccepted'));

    var $controller;
    var $injector;

    var mockedChallengeService;
    var mockedUserService;

    beforeEach(inject(function (_$controller_) {
    // beforeEach(inject(function (_$controller_, _$injector_) {
        $controller = _$controller_;
        // $injector = _$injector_;

        // mockedChallengeService = $injector.get('challengeService');
        // mockedUserService = $injector.get('userService');
    }));

    var $scope, controller;

    var challengeService;

    beforeEach(function () {
        $scope = {};
        challengeService = {};
        // controller = $controller('ChallengeController', {$scope: $scope});
        controller = $controller('ChallengeController', {$scope: $scope, challengeService: mockedChallengeService, userService: mockedUserService});
    });

    it('******', function () {
        console.log(controller);
        console.log(challengeService);
    });

    // it('controller should not return null', function () {
    //     expect(controller).not.toBeNull();
    // });
    //
    // it('createNewChallenge() should not return null', function () {
    //     expect($scope.getUserInputsFromCreateChallengeForm()).not.toBeNull();
    // });
    //
    // it('createNewChallenge() should contain a date, topic and description strings', function () {
    //     expect($scope.getUserInputsFromCreateChallengeForm()).toContain('creationDate', 'topic', 'description');
    // })

});