/**
 *  Copyright 2016-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.antheminc.oss.nimbus.domain.model.state.repo.db;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.util.Assert;

import com.antheminc.oss.nimbus.context.BeanResolverStrategy;
import com.antheminc.oss.nimbus.domain.cmd.CommandMessageConverter;
import com.antheminc.oss.nimbus.domain.config.builder.DomainConfigBuilder;

import lombok.Getter;

/**
 * @author Rakesh Patel
 *
 */
@Getter
public abstract class MongoDBSearch implements DBSearch {

	private final MongoOperations mongoOps;
	
	private final CommandMessageConverter converter;
	
	private final DomainConfigBuilder domainConfigBuilder;
	
	private final BeanResolverStrategy beanResolver;
	
	public MongoDBSearch(BeanResolverStrategy beanResolver) {
		this.beanResolver = beanResolver;
		this.mongoOps = beanResolver.get(MongoOperations.class);
		this.domainConfigBuilder = beanResolver.get(DomainConfigBuilder.class);
		this.converter = beanResolver.get(CommandMessageConverter.class);
	}
	
	
	public <T> Class<?> findOutputClass(SearchCriteria<?> criteria, Class<T> referredClass) {
		if(criteria.getProjectCriteria() != null && StringUtils.isNotBlank(criteria.getProjectCriteria().getAlias())) {
			return getDomainConfigBuilder().getModel(criteria.getProjectCriteria().getAlias()).getReferredClass();
		}
		else if(criteria.getAggregateCriteria() != null ) {
			com.antheminc.oss.nimbus.domain.model.state.repo.ModelRepository.Aggregation aggByAlias = 
					com.antheminc.oss.nimbus.domain.model.state.repo.ModelRepository.Aggregation.getByAlias(criteria.getAggregateCriteria());
			
			Assert.notNull(aggByAlias, "Aggregation constant not found for the alias: " +criteria.getAggregateCriteria());
			
			return aggByAlias.getType();
		}
		
		return referredClass;
	}

}
