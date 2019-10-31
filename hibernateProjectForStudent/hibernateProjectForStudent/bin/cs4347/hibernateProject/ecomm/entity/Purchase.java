����   4 F  -cs4347/hibernateProject/ecomm/entity/Purchase  java/lang/Object id Ljava/lang/Long; purchaseDate Ljava/sql/Date; purchaseAmount D customer /Lcs4347/hibernateProject/ecomm/entity/Customer; product .Lcs4347/hibernateProject/ecomm/entity/Product; <init> ()V Code
     LineNumberTable LocalVariableTable this /Lcs4347/hibernateProject/ecomm/entity/Purchase; getId ()Ljava/lang/Long; RuntimeVisibleAnnotations Ljavax/persistence/Id; "Ljavax/persistence/GeneratedValue; strategy "Ljavax/persistence/GenerationType; IDENTITY	  !   setId (Ljava/lang/Long;)V getPurchaseDate ()Ljava/sql/Date;	  '   setPurchaseDate (Ljava/sql/Date;)V getPurchaseAmount ()D	  - 	 
 setPurchaseAmount (D)V getCustomer 1()Lcs4347/hibernateProject/ecomm/entity/Customer; Ljavax/persistence/OneToOne; fetch Ljavax/persistence/FetchType; EAGER	  7   setCustomer 2(Lcs4347/hibernateProject/ecomm/entity/Customer;)V 
getProduct 0()Lcs4347/hibernateProject/ecomm/entity/Product;	  =   
setProduct 1(Lcs4347/hibernateProject/ecomm/entity/Product;)V 
SourceFile Purchase.java Ljavax/persistence/Entity; Ljavax/persistence/Table; name purchase !                 	 
                     /     *� �                                      e      /     *�  �           &              " #     >     *+�  �       
    +  ,                    $ %     /     *� &�           0              ( )     >     *+� &�       
    5  6                    * +     /     *� ,�           :              . /     >     *'� ,�       
    ?  @                	 
   0 1       2  3e 4 5    /     *� 6�           D              8 9     >     *+� 6�       
    I  J                    : ;       2  3e 4 5    /     *� <�           N              > ?     >     *+� <�       
    S  T                    @    A      B   C  Ds E