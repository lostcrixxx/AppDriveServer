# AppDriveServer
Aplicativo utilizando google drive como servidor

// CODE https://script.google.com/
function doGet(e) {
  var op = e.parameter.action;

  if(op=="readAll")
    return read_all_value(e);
}

function doPost(e) {
  var op = e.parameter.action;
  
  if(op=="insert")
    return insert_value(e);
}

function insert_value(e) {
    var doc = SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/ID_HERE/edit#gid=0");

    var sheet = doc.getSheetByName('database'); // be very careful ... it is the sheet name .. so it should match
    
    var uId = sheet.getLastRow()+1;
    var uName= e.parameter.uName;
    var uImage = e.parameter.uImage;
  
    var dropbox = "Imagens";
    var folder, folders = DriveApp.getFoldersByName(dropbox);
 
    if (folders.hasNext()) {
      folder = folders.next();
    } else {
      folder = DriveApp.createFolder(dropbox);
    }
     
    var fileName = "img_id"+(sheet.getLastRow()+1)+"_"+uName+".jpg";
  
    var contentType = "image/jpg",
        bytes = Utilities.base64Decode(uImage),
        blob = Utilities.newBlob(bytes, contentType,fileName);
    var file = folder.createFile(blob);
    
      file.setSharing(DriveApp.Access.ANYONE_WITH_LINK,DriveApp.Permission.VIEW);
    var fileId=file.getId();
  
    var fileUrl = "https://drive.google.com/uc?export=view&id="+fileId;
        
    sheet.appendRow([uId,uName,fileUrl]);
  
    return ContentService.createTextOutput("Success").setMimeType(ContentService.MimeType.JAVASCRIPT);
}

function read_all_value(request){
  var ss = SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/ID_HERE/edit#gid=0");
 
  //Note : here sheet is sheet name , don't get confuse with other operation 
  //var sheet="database";
  var sheet = ss.getSheetByName('database');

  var records={};
 
  var rows = sheet.getRange(2, 1, sheet.getLastRow() - 1, sheet.getLastColumn()).getValues();
      data = [];

  for (var r = 0, l = rows.length; r < l; r++) {
    var row     = rows[r],
        record  = {};
    record['uId'] = row[0];
    record['uName']=row[1];
    record['uImage']=row[2];
    
    data.push(record);
  }

   records.records = data;
   var result=JSON.stringify(records);
   return ContentService.createTextOutput(result).setMimeType(ContentService.MimeType.JSON);
}
