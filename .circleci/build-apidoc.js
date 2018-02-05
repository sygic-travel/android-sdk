let fs = require('fs');
let path = require('path');
let markdownit = require('./markdown-it')({
    html: true
});

var defaultRender = markdownit.renderer.rules.link_open || function(tokens, idx, options, env, self) {
    return self.renderToken(tokens, idx, options);
};
  
markdownit.renderer.rules.link_open = function (tokens, idx, options, env, self) {
    let aIndex = tokens[idx].attrIndex('href');
    if (aIndex >= 0) {
        let attribute = tokens[idx].attrs[aIndex][1];
        if (attribute.substring(attribute.length - 3) === '.md') {
            tokens[idx].attrs[aIndex][1] = attribute.substring(0, attribute.length - 3) + ".html";
        }
    }

    return defaultRender(tokens, idx, options, env, self);
};

let rootDir = process.argv[2]

processDir(rootDir, "");

function processDir(root, diffPath) {
    let path = root + "/" + diffPath;
    console.log("Processing dir " + path);
    fs.readdir(path, function (err, files) {
        files.forEach(function (file, index) {
            let filePath = path + "/" + file;
            let stat = fs.statSync(filePath);
            if (stat.isDirectory()) {
                processDir(root, diffPath + "/" + file);
            } else if (stat.isFile()) {
                processFile(root, diffPath, file);
            }
        });
    });
}

function processFile(root, diffPath, file) {
    if (file.substring(file.length - 3) !== '.md') {
        return;
    }

    let filename = root + "/" + diffPath + "/" + file;
    console.log("Processing file " + filename);
    let data = fs.readFileSync(filename, {encoding: 'utf-8'});

    let titleRegexp = /^(?:#{1,3})\s+(.+)$/gm;
    let title = (titleRegexp.exec(data) || [null, "Sygic Travel SDK"])[1];
    let upCount = (diffPath.match(/\//g) || []).length;
    let up = "../".repeat(upCount);
    let output = `<html>
    <head>
        <link rel="stylesheet" href="${up}style.css">
        <title>${title}</title>
        <style>
            body { margin: 0; padding: 0; }
            div.header { background: #0366d6; color: white; padding: 10px 20px; }
            div.header a { color: white; }
            div.header h1 { border-bottom: none; margin: 0; padding: 0; }
            div.content { padding: 20px; } 
        </style>
    </head>
    <body>
    <div class="markdown-body">
    <div class="header">
    <a style="float: right;" href="https://github.com/sygic-travel/android-sdk">GitHub repository</a>
    <h1><a href="${up}">Sygic Travel Android SDK</a></h1>
    </div>
    <div class="content">
    ` +
    markdownit.render(data)
    +  `</div></div>
</body>
</html>`;

    let outputFilename = filename.substring(0, filename.length - 2) + "html";
    fs.writeFileSync(outputFilename, output, {encoding: 'utf-8'});
}
