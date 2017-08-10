var btnCust = '<button type="button" class="btn btn-default"' + '>' +
    '<i class="glyphicon glyphicon-tag"></i>' +
    '</button>';

$('#form-photo').fileinput({
    overwriteInitial: false,
    maxFileSize: 1500,
    showClose: false,
    showCaption: false,
    browseLabel: '',
    removeLabel: '',
    browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
    removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
    removeTitle: 'Cancel or reset changes',
    elErrorContainer: '#kv-avatar-errors-1',
    msgErrorClass: 'alert alert-block alert-danger',
    defaultPreviewContent: '<p src="../images/photo.jpg" alt="Your Avatar" style="height:100px">',
    layoutTemplates: {
        main2: '{preview} ' + btnCust + ' {remove} {browse}'
    },
    allowedFileExtensions: ["jpg", "png", "gif"]
});
