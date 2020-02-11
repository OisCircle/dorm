package com.qcq.dorm;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qcq.dorm.mapper.BirdMapper;
import com.qcq.dorm.entity.Bird;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BirdTest {
	@Autowired
	BirdMapper birdMapper;

	int result=0;
	@Test
	public void add() {
		Bird bird = new Bird(-1, "birddy", 2,"birddy's father birggy");

		result = birdMapper.insert(bird);
		System.out.println(result);
	}

	@Test
	public void delete() {
//		result = birdDao.delete(new EntityWrapper<Bird>().eq("name", "birddy"));
		result= birdMapper.delete(new EntityWrapper<Bird>()
				.eq("age", 2)
				.and("id", 1)
		);
		System.out.println(result);
	}
}
