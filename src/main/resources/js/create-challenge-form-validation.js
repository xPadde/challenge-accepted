$(document).ready(function () {

    $('#btn-create-challenge').attr('disabled', true);

    $(document).keyup(function () {
        var isAnyInputEmpty = $('input, textarea').filter(function () {
                return $.trim(this.value).length === 0;
            }).length > 0;

        if (isAnyInputEmpty) {
            $('#btn-create-challenge').attr('disabled', true);
        } else {
            $('#btn-create-challenge').attr('disabled', false);
        }
    });
});