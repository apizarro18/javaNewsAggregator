function Welcome(props){

    if (props.isVisible == false){
      return <div>Welcome {props.username}!</div>
    }
    else{
      return <div>Please log in!</div>
    }
  }

  export default Welcome;