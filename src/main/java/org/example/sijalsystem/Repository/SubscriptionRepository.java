package org.example.sijalsystem.Repository;

import org.example.sijalsystem.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {

    Subscription findSubscriptionById(Integer id);
}
