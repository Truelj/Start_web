function Cinema(){
	this.bookings = new Array();
}
Cinema.prototype.addBooking = function(bookingId, customerName, film, showDate){
	this.bookings[bookingId] = new CustomerBooking(bookingId, customerName, film, showDate);
}
Cinema.prototype.getBookingsTable = function(){
	var booking;
	var bookingTableHTML =  "<table border = 1>";

	for(booking in this.bookings){
		bookingTableHTML += "<tr><td>";
		bookingTableHTML += this.bookings[booking].getBookingId();
		bookingTableHTML += "</td";

		bookingTableHTML += "<td>";
		bookingTableHTML += this.bookings[booking].getCustomerName();
		bookingTableHTML += "</td";

		bookingTableHTML += "<td>";
		bookingTableHTML += this.bookings[booking].getFilm();
		bookingTableHTML += "</td";

		bookingTableHTML += "<td>";
		bookingTableHTML += this.bookings[booking].getShowDate();
		bookingTableHTML += "</td>";
		bookingTableHTML += "</tr>";

	}
	bookingTableHTML += "</table>";

	return bookingTableHTML;
}
