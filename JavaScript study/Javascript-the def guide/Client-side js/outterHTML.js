//Implement the outerHTML property for browsers that do not supportw it
//assumes that the browser does support innerHTML, has an extensible
//Element.prototype, and allows getters and setters to be defined
(function(){
	//if we already have outerHTML return without doing anything
	if(document.createElement('div').outerHTML) return;
	//return the outer HTML of the element referred to by this
	function outerHTMLGetter(){
		var container = documnet.createElement('div');//dummy element
		container.appendChild(this.cloneNode(true));
		return container.innerHTML;
	}
	//set the outer html of the this element to the specified value
	function outerHTMLSetter(value){
		//create a dummy element and set its content to the specified value
		var container = document.createElement('div');
		container.innerHTML = value;
		//move each of the nodes from the dummy into the document
		while(container.firstChild){
			this.parentNode.insertBefore(container.firstChild, this);
		}
		this.parentNode.removeChild(this);
	}
	//now use these two functions as getters and setters for the 
	//outerHTML property of all Element objects. Use ES5 Object.defineProperty
	//if it exists and otherwise fall back on _defineGetter_ and Setter_.
	if(Object.defineProperty){
		Object.defineProperty(Element.prototype,'outerHTML', {
			get: outerHTMLGetter,
			set: outerHTMLSetter,
			enumerable: false, configurable: true
		});
	}else{
		Element.prototype.__defineGetter__('outerHTML', outerHTMLGetter);
		Element.prototype.__defineSetter__('outerHTML', outerHTMLSetter);
	}
}());