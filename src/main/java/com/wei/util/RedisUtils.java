package com.wei.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author WangFan
 * @author XiaoFeng 修改为静态工具类
 * @version 1.1 (GitHub文档: https://github.com/whvcse/RedisUtil )
 * @date 2018-02-24 下午03:09:50
 */
@Component
public class RedisUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    SysErrorLogService sysErrorLogService;

    // -------------------key相关操作---------------------

    /**
     * 删除key
     *
     * @param key 键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys 多个键
     */
    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 序列化key
     *
     * @param key 键
     * @return 字节数组
     */
    public byte[] dump(String key) {
        return stringRedisTemplate.dump(key);
    }

    /**
     * 是否存在key
     *
     * @param key 键
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     *
     * @param key     键
     * @param timeout 过期时间
     * @param unit    单位
     * @return 是否成功
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间
     *
     * @param key  键
     * @param date 日期
     * @return 是否成功
     */
    public Boolean expireAt(String key, Date date) {
        return stringRedisTemplate.expireAt(key, date);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern 正则表达式
     * @return keySet
     */
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key     键
     * @param dbIndex 数据库索引
     * @return 是否成功
     */
    public Boolean move(String key, int dbIndex) {
        return stringRedisTemplate.move(key, dbIndex);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key 键
     * @return 是否成功
     */
    public Boolean persist(String key) {
        return stringRedisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key  键
     * @param unit 单位
     * @return 剩余时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key 键
     * @return 过期时间
     */
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return 键
     */
    public String randomKey() {
        return stringRedisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey 旧的键
     * @param newKey 新的键
     */
    public void rename(String oldKey, String newKey) {
        stringRedisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     *
     * @param oldKey 旧的键
     * @param newKey 新的键
     * @return 是否成功
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return stringRedisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key 键
     * @return 值类型
     */
    public DataType type(String key) {
        return stringRedisTemplate.type(key);
    }

    // -------------------string相关操作---------------------

    /**
     * 设置指定 key 的值
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置指定 key 的值
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    单位
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("redis存储发生异常，查询数据库数据");
//            sysErrorLogService.saveErrorLog("redis写入发生异常",e.getMessage(),"手动记录异常");
        }

    }

    /**
     * 获取指定 key 的值
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        String value = "";
        try {
//            Long expire = getExpire(key);
//            if(expire>1){
                value = stringRedisTemplate.opsForValue().get(key);
//            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("redis查询发生异常，查询数据库数据");
//            sysErrorLogService.saveErrorLog("redis读取发生异常",e.getMessage(),"手动记录异常");
        }

        return value;
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 子字符串
     */
    public String getRange(String key, long start, long end) {
        return stringRedisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key   键
     * @param value 值
     * @return 旧值
     */
    public String getAndSet(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key    键
     * @param offset 偏移量
     * @return 存储在偏移处的原始位值
     */
    public Boolean getBit(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 批量获取
     *
     * @param keys 多个键
     * @return 多个值
     */
    public List<String> multiGet(Collection<String> keys) {
        return stringRedisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     *
     * @param key    键
     * @param offset 位置
     * @param value  值,true为1, false为0
     * @return 存储在偏移处的原始位值
     */
    public Boolean setBit(String key, long offset, boolean value) {
        return stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public void setEx(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key   键
     * @param value 值
     * @return 之前已经存在返回false, 不存在返回true
     */
    public Boolean setIfAbsent(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key    键
     * @param value  值
     * @param offset 从指定位置开始覆写
     */
    public void setRange(String key, String value, long offset) {
        stringRedisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串的长度
     *
     * @param key 键
     * @return 长度
     */
    public Long size(String key) {
        return stringRedisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加
     *
     * @param maps 多个map
     */
    public void multiSet(Map<String, String> maps) {
        stringRedisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps 多个map
     * @return 之前已经存在返回false, 不存在返回true
     */
    public Boolean multiSetIfAbsent(Map<String, String> maps) {
        return stringRedisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key       键
     * @param increment 自增数值
     * @return 增量后的键值
     */
    public Long incrBy(String key, long increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key       键
     * @param increment 自增数值
     * @return 增量后的键值
     */
    public Double incrByFloat(String key, double increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key   键
     * @param value 值
     * @return 增量后的键值
     */
    public Integer append(String key, String value) {
        return stringRedisTemplate.opsForValue().append(key, value);
    }

    // -------------------hash相关操作-------------------------

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    public Object hGet(String key, String field) {
        return stringRedisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key 键
     * @return map
     */
    public Map<Object, Object> hGetAll(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key    键
     * @param fields 字段
     * @return list
     */
    public List<Object> hMultiGet(String key, Collection<Object> fields) {
        return stringRedisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * 增加一个哈希表字段
     *
     * @param key     键
     * @param hashKey hash键
     * @param value   hash值
     */
    public void hPut(String key, String hashKey, String value) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 增加多个哈希表字段
     *
     * @param key  键
     * @param maps 多个map
     */
    public void hPutAll(String key, Map<String, String> maps) {
        stringRedisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key     键
     * @param hashKey hash键
     * @param value   值
     * @return 是否成功
     */
    public Boolean hPutIfAbsent(String key, String hashKey, String value) {
        return stringRedisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key    键
     * @param fields 多个值（可选）
     * @return 删除数量
     */
    public Long hDelete(String key, Object... fields) {
        return stringRedisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key   键
     * @param field 字段
     * @return 是否存在
     */
    public Boolean hExists(String key, String field) {
        return stringRedisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key       键
     * @param field     字段
     * @param increment 自增数值
     * @return 增量后的值
     */
    public Long hIncrBy(String key, Object field, long increment) {
        return stringRedisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key   键
     * @param field 字段
     * @param delta 自增数值
     * @return 增量后的值
     */
    public Double hIncrByFloat(String key, Object field, double delta) {
        return stringRedisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key 键
     * @return 所有字段
     */
    public Set<Object> hKeys(String key) {
        return stringRedisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key 键
     * @return 哈希表长度
     */
    public Long hSize(String key) {
        return stringRedisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key 键
     * @return 所有值
     */
    public List<Object> hValues(String key) {
        return stringRedisTemplate.opsForHash().values(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     键
     * @param options 扫描选项
     * @return 游标
     */
    public Cursor<Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return stringRedisTemplate.opsForHash().scan(key, options);
    }

    // ------------------------list相关操作----------------------------

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   键
     * @param index 索引
     * @return 值
     */
    public String lIndex(String key, long index) {
        return stringRedisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key   键
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return 多个值
     */
    public List<String> lRange(String key, long start, long end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 存储在list头部
     *
     * @param key   键
     * @param value 值
     * @return 操作后列表的长度
     */
    public Long lLeftPush(String key, String value) {
        return stringRedisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 存储在list头部
     *
     * @param key   键
     * @param value 多个值（可选）
     * @return 操作后列表的长度
     */
    public Long lLeftPushAll(String key, String... value) {
        return stringRedisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 存储在list头部
     *
     * @param key   键
     * @param value 多个值
     * @return 操作后列表的长度
     */
    public Long lLeftPushAll(String key, Collection<String> value) {
        return stringRedisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当list存在的时候才加入
     *
     * @param key   键
     * @param value 值
     * @return 操作后列表的长度
     */
    public Long lLeftPushIfPresent(String key, String value) {
        return stringRedisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果pivot存在,再pivot前面添加
     *
     * @param key   键
     * @param pivot pivot
     * @param value 值
     * @return 操作后列表的长度
     */
    public Long lLeftPush(String key, String pivot, String value) {
        return stringRedisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 存储在list尾部
     *
     * @param key   键
     * @param value 值
     * @return 操作后列表的长度
     */
    public Long lRightPush(String key, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 存储在list尾部
     *
     * @param key   键
     * @param value 多个值（可选）
     * @return 操作后列表的长度
     */
    public Long lRightPushAll(String key, String... value) {
        return stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 存储在list尾部
     *
     * @param key   键
     * @param value 多个值
     * @return 操作后列表的长度
     */
    public Long lRightPushAll(String key, Collection<String> value) {
        return stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 为已存在的列表添加值
     *
     * @param key   键
     * @param value 值
     * @return 操作后列表的长度
     */
    public Long lRightPushIfPresent(String key, String value) {
        return stringRedisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 在pivot元素的右边添加值
     *
     * @param key   键
     * @param pivot pivot
     * @param value 值
     * @return 操作后列表的长度
     */
    public Long lRightPush(String key, String pivot, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   键
     * @param index 位置
     * @param value 值
     */
    public void lSet(String key, long index, String value) {
        stringRedisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key 键
     * @return 删除的元素
     */
    public String lLeftPop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     键
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 第一个元素
     */
    public String lLeftPop(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key 键
     * @return 删除的元素
     */
    public String lRightPop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     键
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 最后一个元素
     */
    public String lRightPop(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey      来源key
     * @param destinationKey 目标key
     * @return 最后一个元素
     */
    public String lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return stringRedisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      来源key
     * @param destinationKey 目标key
     * @param timeout        过期时间
     * @param unit           单位
     * @return 值
     */
    public String lRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key   键
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value 值
     * @return list长度
     */
    public Long lRemove(String key, long index, String value) {
        return stringRedisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结果下标
     */
    public void lTrim(String key, long start, long end) {
        stringRedisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key 键
     * @return list长度
     */
    public Long lLen(String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    // --------------------set相关操作--------------------------

    /**
     * set添加元素
     *
     * @param key    键
     * @param values 多个值（可选）
     * @return 添加到集合中的元素数量，不包括已经存在于集合中的所有元素
     */
    public Long sAdd(String key, String... values) {
        return stringRedisTemplate.opsForSet().add(key, values);
    }

    /**
     * set移除元素
     *
     * @param key    键
     * @param values 多个值
     * @return 从集合中删除的成员数，不包括非现有成员。
     */
    public Long sRemove(String key, Object... values) {
        return stringRedisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key 键
     * @return 随机元素
     */
    public String sPop(String key) {
        return stringRedisTemplate.opsForSet().pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key     键
     * @param value   值
     * @param destKey 目标key
     * @return 如果元素被移动，则为true。 如果元素不是源成员且未执行任何操作，则为false。
     */
    public Boolean sMove(String key, String value, String destKey) {
        return stringRedisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取集合的大小
     *
     * @param key 键
     * @return set长度
     */
    public Long sSize(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    public Boolean sIsMember(String key, Object value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取两个集合的交集
     *
     * @param key      键一
     * @param otherKey 键二
     * @return 交集
     */
    public Set<String> sIntersect(String key, String otherKey) {
        return stringRedisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key       键一
     * @param otherKeys 多个其他键
     * @return 交集
     */
    public Set<String> sIntersect(String key, Collection<String> otherKeys) {
        return stringRedisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return set长度
     */
    public Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return stringRedisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     *
     * @param key       键一
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return stringRedisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @return 并集
     */
    public Set<String> sUnion(String key, String otherKeys) {
        return stringRedisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @return 并集
     */
    public Set<String> sUnion(String key, Collection<String> otherKeys) {
        return stringRedisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return 结果集中的元素数
     */
    public Long sUnionAndStore(String key, String otherKey, String destKey) {
        return stringRedisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return stringRedisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key      键一
     * @param otherKey 键二
     * @return 差集
     */
    public Set<String> sDifference(String key, String otherKey) {
        return stringRedisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @return 差集
     */
    public Set<String> sDifference(String key, Collection<String> otherKeys) {
        return stringRedisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return 结果集中的元素数
     */
    public Long sDifference(String key, String otherKey, String destKey) {
        return stringRedisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public Long sDifference(String key, Collection<String> otherKeys, String destKey) {
        return stringRedisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取集合所有元素
     *
     * @param key 键
     * @return 所有元素
     */
    public Set<String> setMembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key 键
     * @return 随机元素
     */
    public String sRandomMember(String key) {
        return stringRedisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key   键
     * @param count 数量
     * @return 随机元素list
     */
    public List<String> sRandomMembers(String key, long count) {
        return stringRedisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key   键
     * @param count 数量
     * @return 随机元素set
     */
    public Set<String> sDistinctRandomMembers(String key, long count) {
        return stringRedisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * 获取Cursor
     *
     * @param key     键
     * @param options 扫描选项
     * @return 游标
     */
    public Cursor<String> sScan(String key, ScanOptions options) {
        return stringRedisTemplate.opsForSet().scan(key, options);
    }

    // ------------------zSet相关操作--------------------------------

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return 是否成功
     */
    public Boolean zAdd(String key, String value, double score) {
        return stringRedisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 添加多个元素
     *
     * @param key    键
     * @param values 多个值
     * @return 添加的数量
     */
    public Long zAdd(String key, Set<TypedTuple<String>> values) {
        return stringRedisTemplate.opsForZSet().add(key, values);
    }

    /**
     * 移除元素
     *
     * @param key    键
     * @param values 多个值（可选）
     * @return 删除的数量
     */
    public Long zRemove(String key, Object... values) {
        return stringRedisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   键
     * @param value 值
     * @param delta delta
     * @return 成员的新分数（双精度浮点数），表示为字符串
     */
    public Double zIncrementScore(String key, String value, double delta) {
        return stringRedisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key   键
     * @param value 值
     * @return 0表示第一位
     */
    public Long zRank(String key, Object value) {
        return stringRedisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key   键
     * @param value 值
     * @return 0表示第一位
     */
    public Long zReverseRank(String key, Object value) {
        return stringRedisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return
     */
    public Set<String> zRange(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public Set<TypedTuple<String>> zRangeWithScores(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 集合元素
     */
    public Set<String> zRangeByScore(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 集合元素
     */
    public Set<TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public Set<TypedTuple<String>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return stringRedisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public Set<String> zReverseRange(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public Set<TypedTuple<String>> zReverseRangeWithScores(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 集合元素
     */
    public Set<String> zReverseRangeByScore(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 集合元素
     */
    public Set<TypedTuple<String>> zReverseRangeByScoreWithScores(
            String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param start 开始下标
     * @param end   结束下标
     * @return 集合元素
     */
    public Set<String> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        return stringRedisTemplate.opsForZSet().reverseRangeByScore(key, min, max,
                start, end);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 数量
     */
    public Long zCount(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return 集合大小
     */
    public Long zSize(String key) {
        return stringRedisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return 集合大小
     */
    public Long zCard(String key) {
        return stringRedisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key   键
     * @param value 值
     * @return score值
     */
    public Double zScore(String key, Object value) {
        return stringRedisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key   键
     * @param start 开始下标
     * @param end   结束下标
     * @return 值
     */
    public Long zRemoveRange(String key, long start, long end) {
        return stringRedisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return stringRedisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return 结果集中的元素数
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        return stringRedisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return stringRedisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 交集
     *
     * @param key      键一
     * @param otherKey 键二
     * @param destKey  目标键
     * @return 结果集中的元素数
     */
    public Long zIntersectAndStore(String key, String otherKey, String destKey) {
        return stringRedisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 交集
     *
     * @param key       键
     * @param otherKeys 多个其他键
     * @param destKey   目标键
     * @return 结果集中的元素数
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return stringRedisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取Cursor
     *
     * @param key     键
     * @param options 扫描选项
     * @return 游标
     */
    public Cursor<TypedTuple<String>> zScan(String key, ScanOptions options) {
        return stringRedisTemplate.opsForZSet().scan(key, options);
    }
}