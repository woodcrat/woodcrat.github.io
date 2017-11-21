function Navbar(){
	return (
		<nav className="navbar navbar-default">
		  <div className="container-fluid">
			<div className="navbar-header">
			  <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span className="icon-bar"></span>
				<span className="icon-bar"></span>
				<span className="icon-bar"></span>
			  </button>
			  <a className="navbar-brand">Group 7</a>
			</div>
			<div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			  <ul className="nav navbar-nav">
				<li><a href="#">Home</a></li>
				<li><a href="#">About</a></li>
				<li><a href="#">Contact</a></li>
				<li><a href="#">News</a></li>
				<li className="dropdown"><a href="#" className="dropdown-toggle" data-toggle="dropdown" role="button">Blog<span className="caret"></span></a>
				  <ul className="dropdown-menu">
					<li><a href="#">Blog entry #1</a></li>
					<li><a href="#">Blog entry #2</a></li>
					<li><a href="#">Blog entry #3</a></li>
					<li><a href="#">Blog entry #4</a></li>
				  </ul>
				</li>
			  </ul>
			</div>
		  </div>
		</nav>		
	);
}