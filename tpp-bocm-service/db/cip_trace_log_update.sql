--扩展请求日志记录响应结果记录长度
ALTER TABLE CIP_TRACE_LOG MODIFY ("RSP_MSG" VARCHAR2(200));