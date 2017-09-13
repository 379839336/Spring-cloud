package com.demo.springcloud;




import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

@Configuration
@EnableScheduling
public class eurekInstanceledListener  implements ApplicationListener{
	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if(applicationEvent instanceof EurekaInstanceCanceledEvent) {
			EurekaInstanceCanceledEvent enent =(EurekaInstanceCanceledEvent) applicationEvent;
			PeerAwareInstanceRegistry registry=EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
			Applications ap=registry.getApplications();
			for (Application application : ap.getRegisteredApplications()) {
				for (InstanceInfo instanceInfo : application.getInstances()) {
					if(instanceInfo.getInstanceId().equals(enent.getServerId())) {
						System.out.println("服务挂了");	
					}
				}
			}
		}
		
		
		if(applicationEvent instanceof EurekaInstanceRegisteredEvent) {
			EurekaInstanceRegisteredEvent event =(EurekaInstanceRegisteredEvent) applicationEvent;
			System.out.println(event.getInstanceInfo().getAppName());
			System.out.println("注册");
		}
		
		if(applicationEvent instanceof EurekaInstanceRenewedEvent) {
			EurekaInstanceRenewedEvent event =(EurekaInstanceRenewedEvent) applicationEvent;
//			System.out.println(event.getAppName());
//			System.out.println("心跳检测");
		}
		
		if(applicationEvent instanceof EurekaRegistryAvailableEvent) {
			System.out.println("开始了");
		}
	}

	

}
