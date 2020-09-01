class AccountReceivable {

    constructor(invoce, state = 'Pendiente') {
        this.invoce = invoce;
        this.paymentDay = this.calculatePaymentDay();
        this.state = state;
    }

    calculatePaymentDay() {
        const numberOfDays = Math.floor(Math.random() * 15);
        const day = new Date();
        day.setDate(day.getDate() + numberOfDays);
        return day;
    }

}

module.exports = AccountReceivable;