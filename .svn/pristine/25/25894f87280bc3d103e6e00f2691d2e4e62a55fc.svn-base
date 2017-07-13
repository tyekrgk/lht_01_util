package lht.wangtong.core.utils.fullseach.test;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lht.wangtong.core.utils.fullseach.DefaultFactory;
import lht.wangtong.core.utils.fullseach.Document;
import lht.wangtong.core.utils.fullseach.DocumentList;
import lht.wangtong.core.utils.fullseach.DocumentWriter;
import lht.wangtong.core.utils.fullseach.FieldType;
import lht.wangtong.core.utils.fullseach.ModeCode;
import lht.wangtong.core.utils.fullseach.Query;
import lht.wangtong.core.utils.fullseach.Reader;
import lht.wangtong.core.utils.fullseach.lucene.FieldName;

import org.apache.commons.lang3.StringUtils;

public class Test {
	public static void main(String[] args) {
		//createIndex();
		//updateIndex();
		//deleteIndex();
		query();
	}

	/**
	 * 创建索引
	 */
	public static void createIndex() {
		List<DocumentWriter> documentWriters = new ArrayList<DocumentWriter>();
		DocumentWriter documentWriter = null;
		documentWriter = new DocumentWriter();
		documentWriter.setModCode("test");
		documentWriter.setDataId("1");
		documentWriter.setTitle("中华人民共和国婚姻法");
		documentWriter.setDataDate(new Date());
		documentWriter.setContent("1950年5月1日公布施行的《中华人民共和国婚姻法》是新中国颁布的第一部法律。全文共分为8章，包括原则、结婚、夫妻间的权利和义务、父母子女间的关系、离婚、离婚后子女的抚养和教育、离婚后的财产和生活及附则，共27条。内容以调整婚姻关系为主，同时涉及家庭关系方面的各种重要问题。1980年9月10日，第五届全国人民代表大会第三次会议通过新的《中华人民共和国婚姻法》；自1981年1月1日起施行，原婚姻法自新法施行之日起废止。2011年8月12日，最高人领冰民法院发布婚姻法最新的,云清");
		documentWriter.addParam("key", "婚姻");
		documentWriter.addRelatedFile(new File("d:\\lucene-index"));
		documentWriters.add(documentWriter);
		documentWriter = new DocumentWriter();
		documentWriter.addField(FieldName.SALES_COUNT, 0, FieldType.LONG);
		documentWriter.setModCode("test");
		documentWriter.setDataId("1");
		documentWriter.setTitle("中华人民共和国婚姻法");
		documentWriter.setDataDate(new Date());
		documentWriter.setContent("1950年5月1日公布施行的《中华人民共和国婚姻法》是新中国颁布的第一部法律。全文共分为8章，包括原则、结婚、夫妻间的权利和义务、父母子女间的关系、离婚、离婚后子女的抚养和教育、离婚后的财产和生活及附则，共27条。内容以调整婚姻关系为主，同时涉及家庭关系方面的各种重要问题。1980年9月10日，第五届全国人民代表大会第三次会议通过新的《中华人民共和国婚姻法》；自1981年1月1日起施行，原婚姻法自新法施行之日起废止。2011年8月12日，最高人领冰民法院发布婚姻法最新的,云清");
		documentWriter.addParam("key", "婚姻");
		documentWriter.addRelatedFile(new File("d:\\lucene-index"));
		documentWriters.add(documentWriter);
		DefaultFactory.createWriter().addAll(documentWriters);
	}

	/**
	 * 更新索引
	 */
	public static void updateIndex() {
		DocumentWriter documentWriter = new DocumentWriter();
		documentWriter.setModCode("test");
		documentWriter.setDataId("1");
		documentWriter.setTitle("中华人民共和国婚姻法");
		documentWriter.setDataDate(new Date());
		documentWriter
				.setContent("1950年5月2日公布施行的《中华人民共和国婚姻法》是新中国颁布的第一部法律。全文共分为8章，包括原则、结婚、夫妻间的权利和义务、父母子女间的关系、离婚、离婚后子女的抚养和教育、离婚后的财产和生活及附则，共27条。内容以调整婚姻关系为主，同时涉及家庭关系方面的各种重要问题。1980年9月10日，第五届全国人民代表大会第三次会议通过新的《中华人民共和国婚姻法》；自1981年1月1日起施行，原婚姻法自新法施行之日起废止。2011年8月12日，最高人民法院发布婚姻法最新的");
		documentWriter.addParam("key", "test");
		DefaultFactory.createWriter().update(documentWriter);
	}

	/**
	 * 删除索引
	 */
	public static void deleteIndex() {
		DefaultFactory.createWriter().delete("test", "1");
	}

	/**
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	public static void query() {
		String key = "注射";// 搜索关键字
		boolean onlyTitle = false;// 只搜索标题
		String modelCode = ModeCode.PRODUCT.getCode(); // 模块代码
		System.out.println(modelCode);
		Date beginDate = null;// 开始日期e
		Date endDate = null;// 结束日期
		int skip = 0;// 开始记录数
		int take = 10;// 分页大小
		DocumentList documentList = null;
		Query query = new Query();
		if (StringUtils.isNotBlank(key)) {
			query.addFindKey(StringUtils.split(key, " "));
		}
		if (onlyTitle) {
			query.setHasTitle(true);
			query.setHasContent(false);
			query.setHasRelatedText(false);
			query.setHasRelatedFile(false);
		} else {
			query.setHasTitle(true);
			query.setHasContent(true);
			query.setHasRelatedText(true);
			query.setHasRelatedFile(true);
		}
		if (StringUtils.isNotEmpty(modelCode)) {
			query.addModCode(modelCode);
		}
		if (beginDate != null || endDate != null) {
			if (beginDate == null) {
				// 2000-01-01 00:00:00
				beginDate = new Date((long) 946656 * 1000000);
			}
			if (endDate == null) {
				// 2099-01-22 00:00:00
				endDate = new Date((long) 40726944 * 100000);
			}
			query.setDate(beginDate, endDate);
		}
		Reader reader = lht.wangtong.core.utils.fullseach.DefaultFactory.createReader();
		reader.addSortDataDate(false);
		reader.setStart(skip);
		reader.setRows(take);
		List<Object> rtn = reader.query(query, true);
		documentList = (DocumentList) rtn.get(0);
		for(Document document : documentList) {
			System.out.println(document.getContent());
		}
		System.out.println("搜索到" + documentList.size() + "记录");
		List<String> dataIds = (List<String>) rtn.get(1);
		for(String id : dataIds) {
			System.out.println(id);
		}
	}
}
