// Copyright 2013 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.
//chrome.windows.create({type:"panel",focused:c,width:d.width,height:d.height//,url:'https://www.youtube.com/'});

//Listener de receber mensagem documentação: https://developer.chrome.com/extensions/messaging
var wId;
chrome.runtime.onMessage.addListener(
  function(request, sender, sendResponse) {
    console.log(sender.tab ?
                "from a content script:" + sender.tab.url :
                "from the extension");
      sendResponse({farewell: "goodbye"});
		 if(wId != null)chrome.windows.remove(wId);
		 chrome.windows.create({
			  url:"http://localhost:8080/youtv/miniplayer/"+ request.vType+"/" +request.vId,
			  type: "panel",
			  focused: true,
			  width: 640,
			  height: 320,	
			}, 
			function(chromeWindow){ 
				wId = chromeWindow.id;
			}					
		);
		
		//chrome.tabs.create({windowId: wId});	 
  });
