// Copyright 2013 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.
//chrome.windows.create({type:"panel",focused:c,width:d.width,height:d.height//,url:'https://www.youtube.com/'});

var proxy = document.querySelector('#proxyCommandOpenPopUp');
proxy.addEventListener('click', openPopUp, false);
function openPopUp(evt){
	chrome.runtime.sendMessage({vId: this.getAttribute("videoId"), vType: this.getAttribute("videoType") });
}