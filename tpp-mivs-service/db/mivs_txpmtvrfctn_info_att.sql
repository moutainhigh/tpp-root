DROP TABLE mivs_txpmtvfctn_info_att;
CREATE TABLE mivs_txpmtvfctn_info_att(
msg_id NVARCHAR2(35) NOT NULL ,
txpmt_inf_nb NUMBER NOT NULL,
tx_auth_cd NVARCHAR2(20) NULL,
tx_auth_nm NVARCHAR2(300) NULL,
txpyr_sts NVARCHAR2(4) NULL
)

;
COMMENT ON TABLE mivs_txpmtvfctn_info_att IS '企业信息联网核查_纳税信息核查业务信息附表';
COMMENT ON COLUMN mivs_txpmtvfctn_info_att.msg_id IS '报文标识号';
COMMENT ON COLUMN mivs_txpmtvfctn_info_att.txpmt_inf_nb IS '条数号';
COMMENT ON COLUMN mivs_txpmtvfctn_info_att.tx_auth_cd IS '税务机关代码';
COMMENT ON COLUMN mivs_txpmtvfctn_info_att.tx_auth_nm IS '税务机关名称';
COMMENT ON COLUMN mivs_txpmtvfctn_info_att.txpyr_sts IS '纳税人状态';

ALTER TABLE mivs_txpmtvfctn_info_att ADD PRIMARY KEY (msg_id, txpmt_inf_nb);