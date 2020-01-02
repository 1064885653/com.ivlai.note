/* 设置url请求 */
window.UEDITOR_CONFIG.serverUrl = "/ue/controller";

/* 自定义菜单 */
window.UEDITOR_CONFIG.toolbars = [[
    'fullscreen', 'source'
    , '|',
    'undo', 'redo'
    , '|',
    'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain'
    , '|',
    'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc'
    , '|',
    'rowspacingtop', 'rowspacingbottom', 'lineheight'
    , '|',
    'customstyle', 'paragraph', 'fontfamily', 'fontsize'
    , '|',
    'directionalityltr', 'directionalityrtl', 'indent'
    , '|',
    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify'
    , '|',
    'touppercase', 'tolowercase'
    , '|',
    'link', 'unlink', 'anchor'
    , '|',
    'imagenone', 'imageleft', 'imageright', 'imagecenter'
    , '|',
    'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo'/*, 'music'*/, 'attachment', 'map'/*, 'gmap'*/, 'insertframe', 'insertcode'/*, 'webapp'*/, 'pagebreak', 'template', 'background'
    , '|',
    'horizontal', 'date', 'time', 'spechars'/*, 'snapscreen'*//*, 'wordimage'*/
    , '|',
    'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts'
    , '|',
    'print', 'preview', 'searchreplace', 'drafts', 'help'
]];

/* xss过滤白名单添加 ifame */
window.UEDITOR_CONFIG.whitList.iframe = ['frameborder', 'border', 'marginwidth', 'marginheight', 'width', 'height', 'src', 'id'];