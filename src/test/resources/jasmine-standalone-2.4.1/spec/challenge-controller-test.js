describe("ChallengeController", function () {

    beforeEach(module('ChallengeAccepted'));

    var $controller;
    beforeEach(inject(function (_$controller_) {
        $controller = _$controller_;
    }));

    var $scope, controller;

    beforeEach(function () {
        $scope = {};
        controller = $controller('ChallengeController', {$scope: $scope});
    });

    it('controller should not return null', function () {
        expect(controller).not.toBeNull();
    });

    it('createNewChallenge() should not return null', function () {
        expect($scope.getUserInputsFromCreateChallengeForm()).not.toBeNull();
    });

    it('createNewChallenge() should contain a date, topic and description strings', function () {
        expect($scope.getUserInputsFromCreateChallengeForm()).toContain('creationDate', 'topic', 'description');
    })

});