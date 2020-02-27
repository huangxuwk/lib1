package com.dl.sd.consumer;

import java.util.List;

import com.dl.multi_file.netWork.NetNode;
import com.dl.sd.registry.INetNode;

/**
 * ͨ����ϣ�㷨��ʵ�ָ��ؾ���<br>
 * �ŵ㣺ʹ��ɢ���㷨����һ���̶ȵķ�ɢ�ͻ��˵ķ���<br>
 * ȱ�㣺��ɢ���㷨���ܽϲ���ؾ��������Ҳ��ܲ�
 * 
 * @author dl
 *
 */
public class HashNodeStrategy implements INodeStrategy {

	@Override
	public INetNode ServerBalance(Consumer consumer, String serviceTag, List<NetNode> nodeList) {
		int tmp = consumer.hashCode() + serviceTag.hashCode();
		int hashCode = Math.abs(tmp ^ (tmp >>> 16));
		int size = nodeList.size();
	
		return nodeList.get(hashCode % (size - 1));
	}

}