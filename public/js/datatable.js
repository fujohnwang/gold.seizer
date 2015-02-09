$(document).ready(function () {
    $('#example').dataTable({
        "processing": true,
        "serverSide": true,
        "ajax": '/data'
    });
});