package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FollowRepository {

	private static final String TABLE_NAME = "follow";
	private final JdbcTemplate jdbcTemplate;

	public Follow save(Follow follow) {
		if (follow.getId() == null) {
			return this.insert(follow);
		}
		throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다.");
	}
	private Follow insert(Follow follow) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
			.withTableName(TABLE_NAME)
			.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
		Long id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Follow.builder()
			.id(id)
			.fromMemberId(follow.getFromMemberId())
			.toMemberId(follow.getToMemberId())
			.createdAt(follow.getCreatedAt())
			.build();
	}
}