package vBox.vboxofficial.dtos;

public class Config {

	private String serverName;
	private String motd;
	private boolean useMysql;
	private boolean useSqlite;
	private boolean useOracle;
	private boolean useMSSql;
	private boolean usePostGres;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getMotd() {
		return motd;
	}

	public void setMotd(String motd) {
		this.motd = motd;
	}

	public boolean useMysql() {
		return useMysql;
	}

	public void useMysql(boolean useMysql) {
		this.useMysql = useMysql;
	}

	public boolean UseSqlite() {
		return useSqlite;
	}

	public void useSqlite(boolean useSqlite) {
		this.useSqlite = useSqlite;
	}

	public boolean useOracle() {
		return useOracle;
	}

	public void useOracle(boolean useOracle) {
		this.useOracle = useOracle;
	}

	public boolean useMSSql() {
		return useMSSql;
	}

	public void useMSSql(boolean useMSSql) {
		this.useMSSql = useMSSql;
	}

	public boolean usePostGres() {
		return usePostGres;
	}

	public void usePostGres(boolean usePostGres) {
		this.usePostGres = usePostGres;
	}
}
