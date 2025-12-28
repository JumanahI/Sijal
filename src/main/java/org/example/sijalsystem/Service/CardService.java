package org.example.sijalsystem.Service;

import lombok.RequiredArgsConstructor;
import org.example.sijalsystem.API.APIException;
import org.example.sijalsystem.Model.Card;
import org.example.sijalsystem.Model.Customer;
import org.example.sijalsystem.Repository.CardRepository;
import org.example.sijalsystem.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CustomerRepository customerRepository;

    public List<Card> getCards(){
        return cardRepository.findAll();
    }

    public void addCard(Integer customer_id , Card card){
        Customer customer = customerRepository.findCustomerByUser_Id(customer_id);
        if(customer == null){
            throw new APIException("Customer not found");
        }
        card.setCustomer(customer);
        cardRepository.save(card);
    }

    public void updateCard(Integer customer_id ,Integer card_id, Card card){
        Customer customer = customerRepository.findCustomerById(customer_id);
        Card oldCard = cardRepository.findCardById(card_id);
        if(customer == null || oldCard == null){
            throw new APIException("Card or Customer not found");
        }
        if(!card.getCustomer().getId().equals(customer.getId())){
            throw new APIException("Customer not authorized to update this card");
        }
        oldCard.setName(card.getName());
        oldCard.setNumber(card.getNumber());
        oldCard.setMonth(card.getMonth());
        oldCard.setYear(card.getYear());
        oldCard.setCvc(card.getCvc());
        cardRepository.save(oldCard);
    }

    public void deleteCard(Integer customer_id, Integer card_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        Card card = cardRepository.findCardById(card_id);
        if(customer == null || card == null){
            throw new APIException("Card or Customer not found");
        }
        if(!card.getCustomer().getId().equals(customer.getId())){
            throw new APIException("Customer not authorized to delete this card");
        }
        cardRepository.delete(card);
    }
}
