function CustomerBooking(bookingId, customerName, film, showDate){
	this.bookingId = bookingId;
	this.customerName = customerName;
	this.film = film;
	this.showDate = showDate;
}
CustomerBooking.prototype.getCustomerName = function (){
	return this.customerName;
}

CustomerBooking.prototype.getShowDate = function(){
	return this.showDate;
}
CustomerBooking.prototype.getFilm = function(){
	return this.film;
}
CustomerBooking.prototype.getBookingId = function(){
	return this.bookingId;
}
CustomerBooking.prototype.setCustomerName = function (customerName){
	this.customerName = customerName;
}

CustomerBooking.prototype.setShowTime = function(showDate){
	this.showDate = showDate;
}
CustomerBooking.prototype.setFilm = function(film){
	this.film = film;
}
CustomerBooking.prototype.setBookingId = function(bookingId){
	this.bookingId = bookingId;
}