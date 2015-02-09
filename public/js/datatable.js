$(document).ready(function () {
    $('#example').dataTable({
        "processing": true,
        "serverSide": true,
        searching: false,
        ordering:  false,
        "ajax": '/data'
    });
});