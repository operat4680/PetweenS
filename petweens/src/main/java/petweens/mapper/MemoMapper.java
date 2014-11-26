package petweens.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import petweens.model.MemoData;


@Repository(value = "memoMapper")
public interface MemoMapper {
	public void insertMemo(@Param("roomid") int roomid,@Param("userid") int userid,@Param("memo") String memo);
	public void deleteMemo(@Param("roomid") int roomid,@Param("userid") int userid);
	public String getMemoById(@Param("roomid") int roomid,@Param("userid") int userid);
	public List<MemoData> getMemoList(int userid);
}
