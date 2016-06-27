package croSNS.friend;

public class FriendVO {
	
	private String sys_id;
	private int sns_list_no;
	private String cus_sns_id;
	
	private int info_no;
	private int cus_no;
	private String cus_name;
	private int cus_age;
	private String cus_gender;
	private String cus_locale;
	private int group_no;
	private String group_name;
	private String cus_memo;
	
	
	public int getSns_list_no() {
		return sns_list_no;
	}

	public void setSns_list_no(int sns_list_no) {
		this.sns_list_no = sns_list_no;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getCus_sns_id() {
		return cus_sns_id;
	}

	public void setCus_sns_id(String cus_sns_id) {
		this.cus_sns_id = cus_sns_id;
	}

	public String getCus_name() {
		return cus_name;
	}

	public void setCus_name(String cus_name) {
		this.cus_name = cus_name;
	}

	public int getCus_age() {
		return cus_age;
	}

	public void setCus_age(int cus_age) {
		this.cus_age = cus_age;
	}

	public String getCus_gender() {
		return cus_gender;
	}

	public void setCus_gender(String cus_gender) {
		this.cus_gender = cus_gender;
	}

	public String getCus_locale() {
		return cus_locale;
	}

	public void setCus_locale(String cus_locale) {
		this.cus_locale = cus_locale;
	}

	public int getCus_no() {
		return cus_no;
	}

	public void setCus_no(int cus_no) {
		this.cus_no = cus_no;
	}

	public int getInfo_no() {
		return info_no;
	}

	public void setInfo_no(int info_no) {
		this.info_no = info_no;
	}

	public int getGroup_no() {
		return group_no;
	}

	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getCus_memo() {
		return cus_memo;
	}

	public void setCus_memo(String cus_memo) {
		this.cus_memo = cus_memo;
	}
	
}
