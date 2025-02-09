class TimeMethod {

	constructor() {}

	//日期格式化
	addZero(data) {
		if (parseInt(data) < 10) {
			return "0" + String(data);
		}
		return data;
	}

	/**
	 * 获取当前日期
	 */
	getNowTime() {
		const myDate = new Date();
		const year = myDate.getFullYear();
		const mouth = this.addZero(myDate.getMonth() + 1);
		const day = this.addZero(myDate.getDate());
		const hour = this.addZero(myDate.getHours());
		const minute = this.addZero(myDate.getMinutes());
		const second = this.addZero(myDate.getSeconds());
		return year + '-' + mouth + '-' + day + 'T' + hour + ':' + minute + ':' + second
	}

	/**
	 * 根据时间返回标准字符串时间
	 * @param {Object} time
	 */
	getTime(time) {
		const myDate = new Date(time);
		const year = myDate.getFullYear();
		const mouth = this.addZero(myDate.getMonth() + 1);
		const day = this.addZero(myDate.getDate());
		const hour = this.addZero(myDate.getHours());
		const minute = this.addZero(myDate.getMinutes());
		const second = this.addZero(myDate.getSeconds());
		return year + '-' + mouth + '-' + day + 'T' + hour + ':' + minute + ':' + second
	}

	/**
	 * @param {Object} timestamp
	 * @param {Object} type
	 * 时间戳转时间
	 */
	timestampToTime(timestamp, type) {
		if (String(timestamp).length === 10) {
			//时间戳为10位需*1000
			var date = new Date(timestamp * 1000);
		} else {
			var date = new Date(timestamp);
		}
		const Y = date.getFullYear() + '-';
		const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
		const D = date.getDate() + ' ';
		const h = date.getHours() + ':';
		const m = date.getMinutes() + ':';
		const s = date.getSeconds();
		if (type === "date") {
			return Y + M + D;
		} else {
			return Y + M + D + h + m + s;
		}
	}


	/**
	 * @param {Object} time
	 * 时间转时间戳
	 */
	timeToTimestamp(time) {
		//精确到秒，毫秒用000代替 ：Date.parse(date); 
		return new Date(time).getTime();
	}


	/**
	 * @param {Object} startTime
	 * @param {Object} endTime
	 * 日期计算
	 */
	calculateTime(startTime, endTime) {
		return new Date(startTime) - new Date(endTime)
	}

	/**
	 * @param {Object} time
	 * 日期转星期
	 */
	getDateToWeek(time) {
		let weekArrayList = [{
				"weekID": 7,
				"weekName": "星期日"
			},
			{
				"weekID": 1,
				"weekName": "星期一"
			},
			{
				"weekID": 2,
				"weekName": "星期二"
			},
			{
				"weekID": 3,
				"weekName": "星期三"
			},
			{
				"weekID": 4,
				"weekName": "星期四"
			},
			{
				"weekID": 5,
				"weekName": "星期五"
			},
			{
				"weekID": 6,
				"weekName": "星期六"
			}
		];
		return weekArrayList[new Date(time).getDay()]
	}

	/**
	 * @param {Object} date
	 *  yyyy-MM-dd HH:mm:ss转为   yyyy-MM-ddTHH:mm:ss
	 */
	timeFormat(date, type) {
		if (type == "T")
			return date.replace(" ", "T")
		else
			return date.replace("T", " ")
	}

	/**
	 * @param {Object} time
	 * 定时器
	 */
	timeSleep(time) {
		return new Promise((resolve) => setTimeout(resolve, time))
	}


	/**
	 * 根据日期加减计算日期
	 * @param dayCount
	 */
	countDateStr(dayCount) {
		let dd = new Date();
		dd.setDate(dd.getDate() + dayCount);
		let ms = dd.getTime();
		return new Date(ms).toJSON();
	}

	/**
	 * @param {Object} time
	 * 日期中时间置0
	 */
	setTimeZero(time) {
		return time.slice(0, 10) + 'T00:00:00.000+00:00';
	}

}


export default new TimeMethod();